package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.UmUserCollect;
import com.zex.cloud.haircut.enums.CollectType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.UmUserCollectMapper;
import com.zex.cloud.haircut.service.IUmUserCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
@Service
public class UmUserCollectServiceImpl extends ServiceImpl<UmUserCollectMapper, UmUserCollect> implements IUmUserCollectService {

    @Override
    public void collect(Long id, CollectType collectType, Long userId) {
        int count =  count(new LambdaQueryWrapper<UmUserCollect>()
                .eq(UmUserCollect::getTargetId,id)
                .eq(UmUserCollect::getTargetType,collectType)
                .eq(UmUserCollect::getUserId,userId));
        if (count > 0){
            throw new ServerException("已经收藏过了哦");
        }
        UmUserCollect userCollect = new UmUserCollect();
        userCollect.setTargetId(id);
        userCollect.setTargetType(collectType);
        userCollect.setUserId(userId);
        save(userCollect);
    }

    @Override
    public void delete(Long id, CollectType collectType, Long userId) {
        remove(new LambdaQueryWrapper<UmUserCollect>().eq(UmUserCollect::getTargetId,id)
        .eq(UmUserCollect::getTargetType,collectType).eq(UmUserCollect::getUserId,userId));
    }
}
