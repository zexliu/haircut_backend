package com.zex.cloud.haircut.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.UmUserHairHistory;
import com.zex.cloud.haircut.vo.UmUserHairHistoryDate;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
public interface UmUserHairHistoryMapper extends BaseMapper<UmUserHairHistory> {

    IPage<UmUserHairHistoryDate> datePage(Page<UmUserHairHistoryDate> page,@Param("userId") Long userId);
}
