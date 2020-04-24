package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SmAgentBrand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
public interface ISmAgentBrandService extends IService<SmAgentBrand> {

    SmAgentBrand create(SmAgentBrand smAgentBrand);

    SmAgentBrand update(SmAgentBrand smAgentBrand);

}
