package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.UmUserPraise;
import com.zex.cloud.haircut.enums.PraiseType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.UmUserPraiseMapper;
import com.zex.cloud.haircut.service.IUmUserPraiseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
@Service
public class UmUserPraiseServiceImpl extends ServiceImpl<UmUserPraiseMapper, UmUserPraise> implements IUmUserPraiseService {

    @Override
    public void praise(Long id, PraiseType praiseType, Long userId) {
        int count = count(new LambdaQueryWrapper<UmUserPraise>()
                .eq(UmUserPraise::getTargetId, id)
                .eq(UmUserPraise::getTargetType, praiseType)
                .eq(UmUserPraise::getUserId, userId));
        if (count > 0) {
            throw new ServerException("已经点过赞了");
        }
        UmUserPraise praise = new UmUserPraise();
        praise.setTargetId(id);
        praise.setTargetType(praiseType);
        praise.setUserId(userId);
        save(praise);
    }

    @Override
    public void delete(Long id, PraiseType praiseType, Long userId) {
        remove(new LambdaQueryWrapper<UmUserPraise>()
                .eq(UmUserPraise::getTargetId, id)
                .eq(UmUserPraise::getTargetType, praiseType)
                .eq(UmUserPraise::getUserId, userId));
    }
}
