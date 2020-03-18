package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmWorkCase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.HmWorkCaseParam;

import java.util.List;

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

    List<HmWorkCase> getByStylistId(Long id);

    void updateWorkCases(Long id, Long shopId, List<HmWorkCaseParam> workCases);
}
