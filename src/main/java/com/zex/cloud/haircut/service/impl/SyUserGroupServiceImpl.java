package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyUserGroup;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SyUserGroupMapper;
import com.zex.cloud.haircut.params.SyUserGroupParam;
import com.zex.cloud.haircut.response.SyUserGroupDetail;
import com.zex.cloud.haircut.service.ISyGroupRoleRelService;
import com.zex.cloud.haircut.service.ISyGroupUserRelService;
import com.zex.cloud.haircut.service.ISyUserGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Service
public class SyUserGroupServiceImpl extends ServiceImpl<SyUserGroupMapper, SyUserGroup> implements ISyUserGroupService {

    @Autowired
    private ISyGroupUserRelService iSyGroupUserRelService;

    @Autowired
    private ISyGroupRoleRelService iSyGroupRoleRelService;

    @Override
    @Transactional
    public SyUserGroup save(SyUserGroupParam param, String operatorIp, Long operatorId) {
        valid(null,param.getName());
        SyUserGroup syUserGroup = adapt(param, operatorIp, operatorId);
        save(syUserGroup);
        iSyGroupRoleRelService.updateRelations(syUserGroup.getId(),param.getRoleIds());

        return syUserGroup;
    }

    @Override
    @Transactional
    public SyUserGroup update(Long id, SyUserGroupParam param, String operatorIp, Long operatorId) {
        valid(id,param.getName());
        SyUserGroup syUserGroup = adapt(param, operatorIp, operatorId);
        syUserGroup.setId(id);
        updateById(syUserGroup);
        iSyGroupRoleRelService.updateRelations(id,param.getRoleIds());
        return syUserGroup;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        removeById(id);
        iSyGroupUserRelService.removeByGroupId(id);
        iSyGroupRoleRelService.removeByGroupId(id);
    }

    @Override
    public SyUserGroupDetail detail(Long id) {
        SyUserGroupDetail detail = new SyUserGroupDetail();
        SyUserGroup group = getById(id);
        BeanUtils.copyProperties(group,detail);
        List<Long> roleIds = iSyGroupRoleRelService.getRoleIdsByGroupId(id);
        detail.setRoleIds(roleIds);
        return detail;
    }

    private void valid(Long id, String name) {
        int count = count(new LambdaQueryWrapper<SyUserGroup>().eq(SyUserGroup::getName,name)
                .ne(id != null,SyUserGroup::getId,id));
        if (count > 0){
            throw new ExistsException("用户组名称已存在");
        }
    }

    private SyUserGroup adapt(SyUserGroupParam param, String operatorIp, Long operatorId) {
        SyUserGroup syUserGroup = new SyUserGroup();
        BeanUtils.copyProperties(param,syUserGroup);
        syUserGroup.setOperatorIp(operatorIp);
        syUserGroup.setOperatorAt(LocalDateTime.now());
        syUserGroup.setOperatorId(operatorId);
        return syUserGroup;
    }
}
