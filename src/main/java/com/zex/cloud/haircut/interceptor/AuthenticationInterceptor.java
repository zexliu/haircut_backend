package com.zex.cloud.haircut.interceptor;

import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.util.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private RedisTemplate<String,Object> redisTemplate;

    public AuthenticationInterceptor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authenticationHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authenticationHeader) && StringUtils.startsWith(authenticationHeader,"Bearer ")){
            String accessToken = authenticationHeader.substring(7);
            RequestUser user = (RequestUser) redisTemplate.opsForValue().get(RedisKeys.accessTokenKey(accessToken));
            RequestHolder.put(user);
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
        super.afterCompletion(request, response, handler, ex);
    }

}
