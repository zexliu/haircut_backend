package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.UmUserCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.CollectType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
public interface IUmUserCollectService extends IService<UmUserCollect> {

    void collect(Long id, CollectType collectType, Long userId);

    void delete(Long id, CollectType collectType, Long userId);

    int shopCount(CollectType collectType, Long targetId);

    boolean isCollect(Long userId, Long targetId, CollectType collectType);

}
