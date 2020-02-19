package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyPermission;
import com.zex.cloud.haircut.enums.PermissionMethodType;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyPermissionParam;
import com.zex.cloud.haircut.response.SyPermissionTree;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyPermissionService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/api/v1/permissions")
@Api(tags = "权限操作相关接口")
public class SyPermissionController {
    @Autowired
    private ISyPermissionService iSyPermissionService;

    @GetMapping
    @ApiOperation("查询权限列表")
    public IPage<SyPermission> list(Pageable page, String keywords, Long moduleId, PermissionMethodType methodType) {
        return iSyPermissionService.list(page, keywords, moduleId, methodType);
    }
    @GetMapping("/tree")
    @ApiOperation("查询权限列表及所属模块的树形结构")
    public List<SyPermissionTree> tree() {
        return iSyPermissionService.tree();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询权限信息")
    public SyPermission one(@PathVariable Long id) {
        return iSyPermissionService.getById(id);
    }

    @PostMapping
    @ApiOperation("新增权限")
    public SyPermission save(@RequestBody @Valid SyPermissionParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyPermissionService.save(param, ip, RequestHolder.user().getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改权限")
    public SyPermission update(@PathVariable Long id, @RequestBody @Valid SyPermissionParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyPermissionService.update(id, param, ip, RequestHolder.user().getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除权限")
    public String delete(@PathVariable Long id) {
        iSyPermissionService.removeById(id);
        return SimpleResp.SUCCESS;
    }
}
