package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.HmJobTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.BaseTitleParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmJobTitleService extends IService<HmJobTitle> {

    HmJobTitle save(BaseTitleParam param);

    HmJobTitle update(Long id, BaseTitleParam param);

    void delete(Long id);
}
