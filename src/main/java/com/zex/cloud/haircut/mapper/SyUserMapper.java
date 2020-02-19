package com.zex.cloud.haircut.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SyUser;
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
public interface SyUserMapper extends BaseMapper<SyUser> {

    IPage<SyUser> list(@Param("keywords") String keywords,
                       @Param("locked") Boolean locked,
                       @Param("enable")Boolean enable,
                       @Param("groupId")Long groupId, Page<SyUser> page);


}
