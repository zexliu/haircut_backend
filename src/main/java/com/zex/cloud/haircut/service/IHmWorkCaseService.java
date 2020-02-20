package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmWorkCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.HmWorkCaseParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmWorkCaseService extends IService<HmWorkCase> {

    void removeByStylistId(Long id);

    HmWorkCase save(HmWorkCaseParam param, Long shopId);

    HmWorkCase update(Long id, HmWorkCaseParam param, Long shopId);

    void delete(Long id, Long shopId);

}
