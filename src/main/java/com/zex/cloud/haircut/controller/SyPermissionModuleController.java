package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyPermissionModule;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyPermissionModuleParam;
import com.zex.cloud.haircut.response.SyPermissionModuleTree;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyPermissionModuleService;
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
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/api/v1/permission/modules")
@Api(tags = "权限模块操作相关接口")
public class SyPermissionModuleController {

    @Autowired
    private ISyPermissionModuleService iSyPermissionModuleService;

    @GetMapping
    @ApiOperation("查询权限模块列表")
    public IPage<SyPermissionModule> list(Pageable page,Long parentId){
        return iSyPermissionModuleService.page(page.convert(),new LambdaQueryWrapper<SyPermissionModule>()
                .eq(parentId != null,SyPermissionModule::getParentId,parentId)
                .orderByAsc(SyPermissionModule::getSeq));
    }

    @GetMapping("/tree")
    @ApiOperation("查询权限模块树")
    public List<SyPermissionModuleTree> tree(){
        return iSyPermissionModuleService.tree();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询权限模块信息")
    public SyPermissionModule one(@PathVariable Long id){
        return iSyPermissionModuleService.getById(id);
    }

    @PostMapping
    @ApiOperation("新增权限模块")
    public SyPermissionModule save(@RequestBody @Valid SyPermissionModuleParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyPermissionModuleService.save(param, ip, RequestHolder.user().getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改权限模块")
    public SyPermissionModule update(@PathVariable Long id ,@RequestBody @Valid SyPermissionModuleParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyPermissionModuleService.update(id,param, ip, RequestHolder.user().getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除权限模块")
    public String delete(@PathVariable Long id){
        iSyPermissionModuleService.delete(id);
        return SimpleResp.SUCCESS;
    }



}
