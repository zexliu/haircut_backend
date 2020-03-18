package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.UmVisitorsLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.vo.ShopVisitorsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-11
 */
public interface IUmVisitorsLogService extends IService<UmVisitorsLog> {

    ShopVisitorsVO shopVisitors(Long shopId);

}
