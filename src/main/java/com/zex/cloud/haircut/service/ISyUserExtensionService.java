package com.zex.cloud.haircut.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.entity.SyUserExtension;
import com.zex.cloud.haircut.params.SyUserExtensionParam;
import com.zex.cloud.haircut.vo.SyUserExtensionVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
public interface ISyUserExtensionService extends IService<SyUserExtension> {

    SyUserExtension getByUnionId(String unionId);

    SyUserExtensionVO extension(Long userId);

    SyUserExtension update(Long id, SyUserExtensionParam param);

}
