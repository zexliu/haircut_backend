package com.zex.cloud.haircut.mapper;

import com.zex.cloud.haircut.entity.SyUserRoleRel;
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
public interface SyUserRoleRelMapper extends BaseMapper<SyUserRoleRel> {

    List<String> queryRolesByUserId(@Param("userId") Long userId);

    List<Long> getRoleIdsByUserId(@Param("userId") Long userId);

}
