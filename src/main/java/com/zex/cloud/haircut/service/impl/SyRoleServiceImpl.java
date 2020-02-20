package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.*;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.SyRoleMapper;
import com.zex.cloud.haircut.params.SyRoleParam;
import com.zex.cloud.haircut.response.AbstractTree;
import com.zex.cloud.haircut.response.SyRoleDetail;
import com.zex.cloud.haircut.response.SyRoleTree;
import com.zex.cloud.haircut.service.ISyRolePermissionRelService;
import com.zex.cloud.haircut.service.ISyRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Service
public class SyRoleServiceImpl extends ServiceImpl<SyRoleMapper, SyRole> implements ISyRoleService {

    @Autowired
    private ISyRolePermissionRelService iSyRolePermissionRelService;

    @Override
    @Transactional
    public SyRole update(Long id, SyRoleParam param, String operatorIp, Long operatorId) {
        valid(id, param.getName());
        SyRole syRole = adapt(param, operatorIp, operatorId);
        syRole.setId(id);
        updateById(syRole);
        iSyRolePermissionRelService.updateRelations(id, param.getPermissionIds());
        return syRole;
    }

    private SyRole adapt(SyRoleParam param, String operatorIp, Long operatorId) {
        SyRole syRole = new SyRole();
        BeanUtils.copyProperties(param, syRole);
        syRole.setOperatorIp(operatorIp);
        syRole.setOperatorAt(LocalDateTime.now());
        syRole.setOperatorId(operatorId);

        return syRole;
    }

    private void valid(Long id, String name) {
        int count = count(new LambdaQueryWrapper<SyRole>().eq(SyRole::getName, name)
                .ne(id != null, SyRole::getId, id));
        if (count > 0) {
            throw new ExistsException("角色名已存在");
        }
    }

    @Override
    public SyRole save(SyRoleParam param, String operatorIp, Long operatorId) {
        valid(null, param.getName());
        SyRole syRole = adapt(param, operatorIp, operatorId);
        save(syRole);
        iSyRolePermissionRelService.updateRelations(syRole.getId(), param.getPermissionIds());
        return syRole;
    }

    @Override
    public List<SyRoleTree> tree() {
        List<SyRoleTree> list = list(new LambdaQueryWrapper<SyRole>().orderByDesc(SyRole::getSeq))
                .stream().flatMap((Function<SyRole, Stream<SyRoleTree>>) role -> {
                    SyRoleTree tree = new SyRoleTree();
                    tree.setName(role.getName());
                    tree.setId(role.getId());
                    tree.setParentId(role.getParentId());
                    return Stream.of(tree);
                }).collect(Collectors.toList());

        return AbstractTree.listToTree(list);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<SyRole> roles = list(new LambdaQueryWrapper<SyRole>().eq(SyRole::getParentId, id));
        if (CollectionUtils.isNotEmpty(roles)) {
            throw new ForbiddenException("无法删除,该角色包含子角色");
        }
        iSyRolePermissionRelService.removeRelationsByRoleId(id);
        removeById(id);
    }

    @Override
    public SyRoleDetail detail(Long id) {
        SyRole role = getById(id);
        SyRoleDetail detail = new SyRoleDetail();
        BeanUtils.copyProperties(role, detail);
        List<Long> permissionIds = iSyRolePermissionRelService.getPermissionIdsByRoleId(id);
        detail.setPermissionIds(permissionIds);
        return detail;
    }


}
