package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmUserReward;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zex.cloud.haircut.enums.UserRewardPublishStatus;
import com.zex.cloud.haircut.enums.UserRewardStatus;
import com.zex.cloud.haircut.vo.OmUserReWardVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-02
 */
public interface OmUserRewardMapper extends BaseMapper<OmUserReward> {

    IPage<OmUserReWardVO> queryUserRewardVO(Page<OmUserReWardVO> convert, @Param("rewardStatus") UserRewardStatus rewardStatus,@Param("publishStatus") UserRewardPublishStatus publishStatus ,@Param("currentUserId") Long currentUserId,@Param("userId") Long userId);

}
