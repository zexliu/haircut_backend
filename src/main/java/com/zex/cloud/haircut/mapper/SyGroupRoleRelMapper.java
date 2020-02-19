package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.SyGroupRoleRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface SyGroupRoleRelMapper extends BaseMapper<SyGroupRoleRel> {

    List<Long> getRoleIdsByGroupId(@Param("groupId") Long groupId);
}
