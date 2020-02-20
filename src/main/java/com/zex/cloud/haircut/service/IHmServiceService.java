package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.BaseTitleParam;
import com.zex.cloud.haircut.params.HmServiceParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmServiceService extends IService<HmService> {

    HmService save(HmServiceParam param);

    HmService update(Long id, HmServiceParam param);

}
