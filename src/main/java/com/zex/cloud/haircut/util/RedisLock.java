package com.zex.cloud.haircut.util;


import io.lettuce.core.RedisFuture;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.async.RedisScriptingAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


/**
 * 只支持springboot2 以后的Redis分布式锁(lettuce底层，不支持jedis)
 * 不支持重入
 * <p>
 * 经过测试，在本地redis情况下，一次lock和releaseLock 总花费约3ms
 */
public class RedisLock {


    private StringRedisTemplate redisTemplate;
    private ThreadLocal<String> lockValue = new ThreadLocal<>();
    private final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private static final String REDIS_LIB_MISMATCH = "Failed to convert nativeConnection. " +
            "Is your SpringBoot main version > 2.0 ? Only lib:lettuce is supported.";
    private static final String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
            "then " +
            "    return redis.call(\"del\",KEYS[1]) " +
            "else " +
            "    return 0 " +
            "end ";


    public RedisLock(StringRedisTemplate redisTemplate) {
        Assert.notNull(redisTemplate, "redisTemplate should not be null.");
        this.redisTemplate = redisTemplate;
    }


    /**
     * 加锁
     *
     * @param key           加锁的key
     * @return
     */

    public boolean lock(String key) {
        return lock(key,10,3,500);
    }



    /**
     * 加锁
     *
     * @param key           加锁的key
     * @param expireSeconds 过期时间
     * @return
     */

    public boolean lock(String key, long expireSeconds) {
        return lock(key,expireSeconds,3,500);
    }


    /**
     * 加锁
     *
     * @param key           加锁的key
     * @param expireSeconds 过期时间
     * @param retryTimes    重试次数
     * @return
     */

    public boolean lock(String key, long expireSeconds, int retryTimes) {
      return lock(key,expireSeconds,retryTimes,500);
    }

    /**
     * 加锁
     *
     * @param key           加锁的key
     * @param expireSeconds 过期时间
     * @param retryTimes    重试次数
     * @param sleepMilliSeconds  重试的间隔  单位秒
     * @return
     */

    public boolean lock(String key, long expireSeconds, int retryTimes, long sleepMilliSeconds) {
        boolean result = tryLock(key, expireSeconds);
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.debug("Lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMilliSeconds);
            } catch (InterruptedException e) {
                return false;
            }
            result = tryLock(key, expireSeconds);
        }
        return result;
    }


    /**
     * 加锁 不重试直接返回是否获取锁成功  默认锁过期时间10秒
     * @param key Key
     * @return
     */

    public boolean isLock(String key) {
        return tryLock(key, 10);
    }

    /**
     * 加锁 不重试直接返回是否获取锁成功
     * @param key Key
     * @param expiredSeconds  锁的过期时间
     * @return
     */
    public boolean isLock(String key, long expiredSeconds) {
        return tryLock(key, expiredSeconds);

    }

    /**
     * 尝试Lock
     *
     * @param key
     * @param expireSeconds
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean tryLock(String key, long expireSeconds) {

        String uuid = UUID.randomUUID().toString();
        try {
            String result = redisTemplate.execute(new RedisCallback<String>() {

                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        Object nativeConnection = connection.getNativeConnection();

                        byte[] keyByte = key.getBytes(StandardCharsets.UTF_8);
                        byte[] valueByte = uuid.getBytes(StandardCharsets.UTF_8);

                        String resultString = "";
                        if (nativeConnection instanceof RedisAsyncCommands) {
                            RedisAsyncCommands command = (RedisAsyncCommands) nativeConnection;
                            resultString = command
                                    .getStatefulConnection()
                                    .sync()
                                    .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expireSeconds));
                        } else if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                            RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                            resultString = clusterAsyncCommands
                                    .getStatefulConnection()
                                    .sync()
                                    .set(keyByte, keyByte, SetArgs.Builder.nx().ex(expireSeconds));
                        } else {
                            logger.error(REDIS_LIB_MISMATCH);
                        }
                        return resultString;
                    } catch (Exception e) {
                        logger.error("Failed to lock, closing connection", e);
                        closeConnection(connection);
                        return "";
                    }
                }
            });
            boolean eq = "OK".equals(result);
            if (eq) {
                lockValue.set(uuid);
            }
            return eq;
        } catch (Exception e) {
            logger.error("Set redis exception", e);
            return false;
        }
    }

    /**
     * 释放锁
     * 有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
     * 使用lua脚本删除redis中匹配value的key
     *
     * @param key
     * @return false:   锁已不属于当前线程  或者 锁已超时
     */
    @SuppressWarnings("unchecked")

    public boolean releaseLock(String key) {
        try {
            String lockValue = this.lockValue.get();
            if (lockValue == null) {
                return false;
            }
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] valueBytes = lockValue.getBytes(StandardCharsets.UTF_8);
            Object[] keyParam = new Object[]{keyBytes};

            Long result = redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        Object nativeConnection = connection.getNativeConnection();
                        if (nativeConnection instanceof RedisScriptingAsyncCommands) {
                            /**
                             * 不要问我为什么这里的参数这么奇怪
                             */
                            RedisScriptingAsyncCommands<Object, byte[]> command = (RedisScriptingAsyncCommands<Object, byte[]>) nativeConnection;
                            RedisFuture future = command.eval(UNLOCK_LUA, ScriptOutputType.INTEGER, keyParam, valueBytes);
                            return getEvalResult(future, connection);
                        } else {
                            logger.warn(REDIS_LIB_MISMATCH);
                            return 0L;
                        }
                    } catch (Exception e) {
                        logger.error("Failed to releaseLock, closing connection", e);
                        closeConnection(connection);
                        return 0L;
                    }
                }
            });
            return result != null && result > 0;
        } catch (Exception e) {
            logger.error("release lock exception", e);
        }
        return false;
    }

    private Long getEvalResult(RedisFuture future, RedisConnection connection) {
        try {
            Object o = future.get();
            return (Long) o;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Future get failed, trying to close connection.", e);
            closeConnection(connection);
            return 0L;
        }
    }


    private void closeConnection(RedisConnection connection) {
        try {
            connection.close();
        } catch (Exception e2) {
            logger.error("close connection fail.", e2);
        }
    }

//    /**
//     * 查看是否加锁
//     *
//     * @param key
//     * @return
//     */
//
//    public boolean isLocked(String key) {
//        Object o = redisTemplate.opsForValue().get(key);
//        return o != null;
//    }
}
