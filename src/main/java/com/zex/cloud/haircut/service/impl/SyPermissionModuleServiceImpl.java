package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.entity.SyPermission;
import com.zex.cloud.haircut.entity.SyPermissionModule;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.SyPermissionModuleMapper;
import com.zex.cloud.haircut.params.SyPermissionModuleParam;
import com.zex.cloud.haircut.response.AbstractTree;
import com.zex.cloud.haircut.response.SyPermissionModuleTree;
import com.zex.cloud.haircut.service.ISyPermissionModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISyPermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SyPermissionModuleServiceImpl extends ServiceImpl<SyPermissionModuleMapper, SyPermissionModule> implements ISyPermissionModuleService {

    @Autowired
    private ISyPermissionService iSyPermissionService;

    @Override
    public SyPermissionModule save(SyPermissionModuleParam param, String operatorIp, Long operatorId) {
        valid(null, param.getName(), param.getParentId());
        SyPermissionModule module = adapt(param, operatorIp, operatorId);
        save(module);
        return module;
    }

    @Override
    public SyPermissionModule update(Long id, SyPermissionModuleParam param, String operatorIp, Long operatorId) {
        valid(id, param.getName(), param.getParentId());
        SyPermissionModule module = adapt(param, operatorIp, operatorId);
        module.setId(id);
        updateById(module);
        return module;
    }

    @Override
    public void delete(Long id) {
        List<SyPermissionModule> modules = list(new LambdaQueryWrapper<SyPermissionModule>().eq(SyPermissionModule::getParentId, id));
        if (CollectionUtils.isNotEmpty(modules)) {
            throw new ForbiddenException("无法删除,该模块包含子模块");
        }
        List<SyPermission> permissions = iSyPermissionService.listByModuleId(id);
        if (CollectionUtils.isNotEmpty(permissions)) {
            throw new ForbiddenException("无法删除,该模块已关联权限");
        }
        removeById(id);
    }

    @Override
    public List<SyPermissionModuleTree> tree() {
        List<SyPermissionModuleTree> list = list(new LambdaQueryWrapper<SyPermissionModule>().orderByAsc(SyPermissionModule::getSeq)).stream().flatMap((Function<SyPermissionModule, Stream<SyPermissionModuleTree>>) permissionModule -> {
            SyPermissionModuleTree module = new SyPermissionModuleTree();
            module.setName(permissionModule.getName());
            module.setId(permissionModule.getId());
            module.setParentId(permissionModule.getParentId());
            return Stream.of(module);
        }).collect(Collectors.toList());
        return AbstractTree.listToTree(list);
    }

    private void valid(Long id, String name, Long parentId) {

        int count = count(new LambdaQueryWrapper<SyPermissionModule>()
                .eq(SyPermissionModule::getName, name).eq(SyPermissionModule::getParentId, parentId)
                .ne(id != null, SyPermissionModule::getId, id));
        if (count > 0) {
            throw new ExistsException("该名称已存在");
        }
    }

    private SyPermissionModule adapt(SyPermissionModuleParam param, String operatorIp, Long operatorId) {

        SyPermissionModule syPermissionModule = new SyPermissionModule();
        BeanUtils.copyProperties(param, syPermissionModule);
        syPermissionModule.setOperatorIp(operatorIp);
        syPermissionModule.setOperatorAt(LocalDateTime.now());
        syPermissionModule.setOperatorId(operatorId);
        return syPermissionModule;
    }
}
