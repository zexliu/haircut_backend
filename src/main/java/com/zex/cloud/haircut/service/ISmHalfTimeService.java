package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmHalfTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SmHalfTimeParam;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-21
 */
public interface ISmHalfTimeService extends IService<SmHalfTime> {

//    void updateRelations(Long shopId, List<SmHalfTimeParam> params);

    boolean valid(Long shopId, LocalDateTime appointmentAt);

    SmHalfTime create(SmHalfTimeParam param);

    SmHalfTime update(Long id, SmHalfTimeParam param);

    Boolean isHalf(LocalDateTime dateTime);
}
