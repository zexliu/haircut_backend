package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.SyRoleParam;
import com.zex.cloud.haircut.response.SyRoleDetail;
import com.zex.cloud.haircut.response.SyRoleTree;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyRoleService extends IService<SyRole> {

    SyRole update(Long id, SyRoleParam param, String operatorIp, Long operatorId);

    SyRole save(SyRoleParam param, String operatorIp, Long operatorId);

    List<SyRoleTree> tree();

    void delete(Long id);

    SyRoleDetail detail(Long id);

}
