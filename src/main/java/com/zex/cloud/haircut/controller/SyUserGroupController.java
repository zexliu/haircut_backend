package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyUserGroup;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.SyUserGroupParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.SyUserGroupDetail;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyUserGroupService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/api/v1/user/groups")
@Api(tags = "用户组操作相关接口")
public class SyUserGroupController {

    @Autowired
    private ISyUserGroupService iSyUserGroupService;

    @GetMapping
    @ApiOperation("查询用户组列表")
    public IPage<SyUserGroup> list(Pageable pageable){
        return iSyUserGroupService.page(pageable.convert(),new LambdaQueryWrapper<SyUserGroup>()
                .orderByDesc(SyUserGroup::getSeq));
    }

    @GetMapping("/{id}")
    @ApiOperation("查询用户组信息")
    public SyUserGroupDetail detail(@PathVariable Long id){
        return iSyUserGroupService.detail(id);
    }



    @PostMapping
    @ApiOperation("新增用户组")
    public SyUserGroup save(@RequestBody @Valid SyUserGroupParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyUserGroupService.save(param, ip, RequestHolder.user().getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户组")
    public SyUserGroup update(@PathVariable Long id ,@RequestBody @Valid SyUserGroupParam param, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyUserGroupService.update(id,param, ip, RequestHolder.user().getId());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户组")
    public String delete(@PathVariable Long id){
        iSyUserGroupService.delete(id);
        return SimpleResp.SUCCESS;
    }
}
