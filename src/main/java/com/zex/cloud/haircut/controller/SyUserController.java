package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.params.PasswordCurrentParam;
import com.zex.cloud.haircut.params.PasswordParam;
import com.zex.cloud.haircut.params.SyUserParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.response.SyUserDetail;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.ISyUserService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "用户操作相关接口")
public class SyUserController {

    @Autowired
    private ISyUserService iSyUserService;

    @GetMapping
    @ApiOperation("查询用户列表")
    public IPage<SyUser> list(String keywords, Boolean locked, Boolean enable, Long groupId, Pageable page) {
        return iSyUserService.list(keywords, locked, enable, groupId, page.convert());
    }

    @GetMapping("/{id}")
    @ApiOperation("查询用户信息")
    public SyUserDetail detail(@PathVariable Long id) {
        return iSyUserService.detail(id);
    }

    @PostMapping
    @ApiOperation("新增用户")
    public SyUser save(@RequestBody @Valid SyUserParam syUserParam, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyUserService.save(syUserParam, ip, RequestHolder.user().getId());
    }

    @PutMapping("/{id}")
    @ApiOperation("修改用户")
    public SyUser update(@PathVariable Long id, @RequestBody @Valid SyUserParam syUserParam, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        return iSyUserService.update(id, syUserParam, ip, RequestHolder.user().getId());
    }

    @PutMapping("admin/{id}/password")
    @ApiOperation("管理员修改密码")
    public String adminPassword(@PathVariable Long id, String password, HttpServletRequest request) {
        String ip = NetWorkUtils.getRemoteHost(request);
        iSyUserService.adminPassword(id, password, ip, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }


    @PostMapping("/password")
    @ApiOperation("通过账号和旧密码修改密码")
    public String password(@RequestBody PasswordParam param) {
        iSyUserService.password(param);
        return SimpleResp.SUCCESS;
    }

    @PostMapping("/password/current")
    @ApiOperation("修改当前访问账号的密码")
    public String passwordCurrent(@RequestBody PasswordCurrentParam param) {
        iSyUserService.passwordCurrent(param,RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public String delete(@PathVariable Long id) {
        iSyUserService.removeById(id);
        return SimpleResp.SUCCESS;
    }

    @GetMapping("/current")
    @ApiOperation("获取当前用户信息")
    public RequestUser token() {
        return RequestHolder.user();
    }

}
