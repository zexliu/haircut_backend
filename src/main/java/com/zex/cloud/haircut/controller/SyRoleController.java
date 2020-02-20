package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyRole;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyRoleParam;
import com.zex.cloud.haircut.response.SyRoleDetail;
import com.zex.cloud.haircut.response.SyRoleTree;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyRoleService;
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
@RequestMapping("/api/v1/roles")
@Api(tags = "角色操作相关接口")
public class SyRoleController {
    @Autowired
    private ISyRoleService iSyRoleService;

    @GetMapping
    @ApiOperation("查询角色列表")
    public IPage<SyRole> list(Pageable page,Long parentId){
        return iSyRoleService.page(page.convert(),new LambdaQueryWrapper<SyRole>()
                .eq(parentId != null,SyRole::getParentId,parentId)
                .orderByDesc(SyRole::getSeq));
    }

    @GetMapping("/tree")
    @ApiOperation("查询角色树")
    public List<SyRoleTree> tree(){
        return iSyRoleService.tree();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询角色信息")
    public SyRoleDetail one(@PathVariable Long id){
        return iSyRoleService.detail(id);
    }

    @PostMapping
    @ApiOperation("新增角色")
    public SyRole save(@RequestBody @Valid SyRoleParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyRoleService.save(param, ip, RequestHolder.user().getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改角色")
    public SyRole update(@PathVariable Long id ,@RequestBody @Valid SyRoleParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyRoleService.update(id,param, ip, RequestHolder.user().getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    public String delete(@PathVariable Long id){
        iSyRoleService.delete(id);
        return SimpleResp.SUCCESS;
    }

}
