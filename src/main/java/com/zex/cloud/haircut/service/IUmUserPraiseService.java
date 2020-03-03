package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.UmUserPraise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.PraiseType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
public interface IUmUserPraiseService extends IService<UmUserPraise> {

    void praise(Long id, PraiseType praiseType, Long userId);

    void delete(Long id, PraiseType praiseType, Long userId);
}
