package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyRolePermissionRel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyRolePermissionRelService extends IService<SyRolePermissionRel> {

    void updateRelations(Long id, List<Long> permissionIds);

    void removeRelationsByRoleId(Long id);

    List<Long> getPermissionIdsByRoleId(Long id);
}
