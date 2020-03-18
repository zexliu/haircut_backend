package com.zex.cloud.haircut.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.UmUserHairHistory;
import com.zex.cloud.haircut.params.UmUserHairHistoryParam;
import com.zex.cloud.haircut.vo.UmUserHairHistoryDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
public interface IUmUserHairHistoryService extends IService<UmUserHairHistory> {

    UmUserHairHistory create(UmUserHairHistoryParam param, Long userId);

    UmUserHairHistory update(Long id, UmUserHairHistoryParam param, Long userId);

    void delete(Long id, Long userId);

    IPage<UmUserHairHistoryDate> datePage(Page<UmUserHairHistoryDate> page, Long id);
}
