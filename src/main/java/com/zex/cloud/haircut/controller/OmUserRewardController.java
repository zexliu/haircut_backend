package com.zex.cloud.haircut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zex.cloud.haircut.entity.OmUserReward;
import com.zex.cloud.haircut.enums.UserRewardPublishStatus;
import com.zex.cloud.haircut.enums.UserRewardStatus;
import com.zex.cloud.haircut.params.Pageable;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOmUserRewardService;
import com.zex.cloud.haircut.vo.OmUserReWardVO;
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
@RequestMapping("/api/v1/rewards")
@Api(tags = "悬赏动态")
public class OmUserRewardController {

    @Autowired
    private IOmUserRewardService iOmUserRewardService;

    @GetMapping
    @ApiOperation("获取悬赏动态列表")
    public IPage<OmUserReWardVO> page(Pageable pageable, UserRewardStatus rewardStatus, UserRewardPublishStatus publishStatus,Long userId,Integer deleteStatus){
        return iOmUserRewardService.page(pageable.convert(),rewardStatus,publishStatus,RequestHolder.user().getId(),userId,deleteStatus);
    }

    @GetMapping("/current")
    @ApiOperation("当前用户获取悬赏动态列表")
    public IPage<OmUserReWardVO> page(Pageable pageable, UserRewardStatus rewardStatus, UserRewardPublishStatus publishStatus){
        return iOmUserRewardService.page(pageable.convert(),rewardStatus,publishStatus,RequestHolder.user().getId(),RequestHolder.user().getId(),0);
    }
    @ApiOperation("悬赏")
    @PutMapping("/rewardStatus/{id}")
    public String rewardStatus(@PathVariable Long id,@RequestParam Long commentId){
         iOmUserRewardService.rewardStatus(id,commentId, RequestHolder.user().getId());
        return SimpleResp.SUCCESS;
    }


    @ApiOperation("删除悬赏动态")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id ){
        iOmUserRewardService.remove(id);
        return SimpleResp.SUCCESS;
    }


}
