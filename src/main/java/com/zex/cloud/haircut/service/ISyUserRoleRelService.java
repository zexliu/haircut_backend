package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyUserRoleRel;
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
public interface ISyUserRoleRelService extends IService<SyUserRoleRel> {
    void updateRelations(Long id, List<Long> roleIds);

    List<String> getRequestRoles(Long userId);

    List<Long> getRoleIdsByUserId(Long id);

}
