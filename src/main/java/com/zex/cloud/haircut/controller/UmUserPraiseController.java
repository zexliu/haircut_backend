package com.zex.cloud.haircut.controller;


import com.zex.cloud.haircut.enums.CollectType;
import com.zex.cloud.haircut.enums.PraiseType;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.IUmUserCollectService;
import com.zex.cloud.haircut.service.IUmUserPraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/api/v1/praise")
@Api(tags = "点赞")
public class UmUserPraiseController {

    @Autowired
    private IUmUserPraiseService iUmUserPraiseService;

    @PostMapping("/{id}")
    @ApiOperation("点赞")
    public String praise(@PathVariable Long id , @RequestParam PraiseType praiseType){
        iUmUserPraiseService.praise(id,praiseType, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("取消点赞")
    public String delete(@PathVariable Long id , @RequestParam PraiseType praiseType){
        iUmUserPraiseService.delete(id,praiseType, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }

}
