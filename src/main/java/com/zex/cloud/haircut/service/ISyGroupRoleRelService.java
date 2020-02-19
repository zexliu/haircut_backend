package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyGroupRoleRel;
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
public interface ISyGroupRoleRelService extends IService<SyGroupRoleRel> {

    void removeByGroupId(Long id);

    List<Long> getRoleIdsByGroupId(Long id);

    void updateRelations(Long id, List<Long> roleIds);

}
