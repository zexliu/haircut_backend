package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.UmPopularizeQrCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.security.RequestUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-06
 */
public interface IUmPopularizeQrCodeService extends IService<UmPopularizeQrCode> {

    UmPopularizeQrCode getQrCode(RequestUser user);

}
