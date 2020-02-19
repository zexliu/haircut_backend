package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.PermissionMethodType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyPermissionParam;
import com.zex.cloud.haircut.response.SyPermissionTree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyPermissionService extends IService<SyPermission> {

    SyPermission save(SyPermissionParam param, String operatorIp, Long operatorId);

    SyPermission update(Long id, SyPermissionParam param, String operatorIp, Long operatorId);

    List<SyPermission> listByModuleId(Long id);

    IPage<SyPermission> list(Pageable page, String keywords, Long moduleId, PermissionMethodType methodType);

    List<SyPermissionTree> tree();

}
