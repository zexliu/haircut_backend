package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyUserGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SyUserGroupParam;
import com.zex.cloud.haircut.response.SyUserGroupDetail;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyUserGroupService extends IService<SyUserGroup> {

    SyUserGroup save(SyUserGroupParam param, String operatorIp, Long operatorId);

    SyUserGroup update(Long id, SyUserGroupParam param, String operatorIp, Long operatorId);

    void delete(Long id);

    SyUserGroupDetail detail(Long id);
}
