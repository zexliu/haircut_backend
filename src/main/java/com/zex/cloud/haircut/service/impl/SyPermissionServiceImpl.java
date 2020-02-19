package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.zex.cloud.haircut.entity.SyPermission;
import com.zex.cloud.haircut.entity.SyPermissionModule;
import com.zex.cloud.haircut.enums.PermissionMethodType;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SyPermissionMapper;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyPermissionParam;
import com.zex.cloud.haircut.response.AbstractTree;
import com.zex.cloud.haircut.response.SyPermissionTree;
import com.zex.cloud.haircut.service.ISyPermissionModuleService;
import com.zex.cloud.haircut.service.ISyPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@Service
public class SyPermissionServiceImpl extends ServiceImpl<SyPermissionMapper, SyPermission> implements ISyPermissionService {

    @Autowired
    private ISyPermissionModuleService iSyPermissionModuleService;
    @Override
    public SyPermission save(SyPermissionParam param, String operatorIp, Long operatorId) {
        valid(null,param.getMethodType(),param.getName(),param.getUrl());
        SyPermission syPermission = adapt(param, operatorIp, operatorId);
        save(syPermission);
        return syPermission;
    }

    private SyPermission adapt(SyPermissionParam param, String operatorIp, Long operatorId) {

        SyPermission syPermission = new SyPermission();
        BeanUtils.copyProperties(param,syPermission);
        syPermission.setOperatorIp(operatorIp);
        syPermission.setOperatorAt(LocalDateTime.now());
        syPermission.setOperatorId(operatorId);
        return syPermission;
    }

    @Override
    public SyPermission update(Long id, SyPermissionParam param, String operatorIp, Long operatorId) {

        valid(id,param.getMethodType(),param.getName(),param.getUrl());
        SyPermission syPermission = adapt(param, operatorIp, operatorId);
        syPermission.setId(id);
        updateById(syPermission);

        return syPermission;
    }

    @Override
    public List<SyPermission> listByModuleId(Long id) {
        return list(new LambdaQueryWrapper<SyPermission>().eq(SyPermission::getModuleId,id));
    }

    @Override
    public IPage<SyPermission> list(Pageable page, String keywords, Long moduleId, PermissionMethodType methodType) {
        return page(page.convert(),new LambdaQueryWrapper<SyPermission>()
                .nested(StringUtils.isNotBlank(keywords),i->
                        i.like(SyPermission::getName,keywords)
                        .or().like(SyPermission::getUrl,keywords))
                .eq(moduleId != null,SyPermission::getModuleId,moduleId)
                .eq(methodType != null,SyPermission::getMethodType,methodType)
                .orderByAsc(SyPermission::getSeq)
        );
    }

    @Override
    public List<SyPermissionTree> tree() {
        List<SyPermissionTree> modules = iSyPermissionModuleService.list()
                .stream()
                .flatMap((Function<SyPermissionModule, Stream<SyPermissionTree>>) module -> {
            SyPermissionTree tree = new SyPermissionTree();
            tree.setId(module.getId());
            tree.setName(module.getName());
            tree.setParentId(module.getParentId());
            tree.setModule(true);
            return Stream.of(tree);
        }).collect(Collectors.toList());

        List<SyPermissionTree> permissions = list().stream().flatMap((Function<SyPermission, Stream<SyPermissionTree>>) permission -> {
            SyPermissionTree tree = new SyPermissionTree();
            tree.setId(permission.getId());
            tree.setName(permission.getName());
            tree.setParentId(permission.getModuleId());
            tree.setModule(false);
            return Stream.of(tree);
        }).collect(Collectors.toList());

        List<SyPermissionTree> all = Lists.newArrayList();
        all.addAll(modules);
        all.addAll(permissions);
        return AbstractTree.listToTree(all);
    }

    private void valid(Long id, PermissionMethodType methodType, String name, String url) {
        int count = count(new LambdaQueryWrapper<SyPermission>().nested(i -> i.eq(SyPermission::getMethodType,methodType)
        .eq(SyPermission::getUrl,url).or().eq(SyPermission::getName,name))
                .ne(id != null,SyPermission::getId,id));
        if (count > 0 ){
            throw new ExistsException("已存在该权限路径或名称");
        }
    }
}
