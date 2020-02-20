package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.params.AmAuditParam;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IAmAuditHistoryService;
import com.zex.cloud.haircut.util.NetWorkUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/api/v1/audits")
@Api(tags = "审核操作相关接口")
public class AmAuditController {

    @Autowired
    private IAmAuditHistoryService iAmAuditHistoryService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    @ApiOperation("审核记录")
    public String audit(@RequestBody @Valid AmAuditParam param){
        String ip = NetWorkUtils.getRemoteHost(request);
        iAmAuditHistoryService.audit(param,ip, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }


}
