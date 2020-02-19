package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyPermissionModule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SyPermissionModuleParam;
import com.zex.cloud.haircut.response.SyPermissionModuleTree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyPermissionModuleService extends IService<SyPermissionModule> {

    SyPermissionModule save(SyPermissionModuleParam param, String operatorIp, Long operatorId);

    SyPermissionModule update(Long id, SyPermissionModuleParam param, String operatorIp, Long operatorId);

    void delete(Long id);

    List<SyPermissionModuleTree> tree();


}
