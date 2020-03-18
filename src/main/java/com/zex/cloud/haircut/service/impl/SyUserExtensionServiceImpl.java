package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.entity.SyUserExtension;
import com.zex.cloud.haircut.mapper.SyUserExtensionMapper;
import com.zex.cloud.haircut.params.SyUserExtensionParam;
import com.zex.cloud.haircut.security.RequestHolder;
import com.zex.cloud.haircut.service.ISyUserExtensionService;
import com.zex.cloud.haircut.service.ISyUserService;
import com.zex.cloud.haircut.vo.SyUserExtensionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-16
 */
@Service
public class SyUserExtensionServiceImpl extends ServiceImpl<SyUserExtensionMapper, SyUserExtension> implements ISyUserExtensionService {


    @Autowired
    private ISyUserService iSyUserService;

    @Override
    public SyUserExtension getByUnionId(String unionId) {
        return getOne(new LambdaQueryWrapper<SyUserExtension>().eq(SyUserExtension::getUnionId,unionId));
    }

    @Override
    public SyUserExtensionVO extension(Long userId) {
        SyUserExtension syUserExtension = getByUserId(userId);
        SyUserExtensionVO vo = new SyUserExtensionVO();
        BeanUtils.copyProperties(syUserExtension,vo);
        SyUser syUser = iSyUserService.getById(userId);
        vo.setGender(syUser.getGender());
        return vo;
    }

    @Override
    public SyUserExtension update(Long userId, SyUserExtensionParam param) {
        SyUserExtension syUserExtension = getByUserId(userId);
        BeanUtils.copyProperties(param,syUserExtension);
        updateById(syUserExtension);
        SyUser syUser = iSyUserService.getById(userId);
        syUser.setGender(param.getGender());
        iSyUserService.updateById(syUser);
        return syUserExtension;
    }

    private SyUserExtension getByUserId(Long userId) {
        SyUserExtension extension = getOne(new LambdaQueryWrapper<SyUserExtension>()
        .eq(SyUserExtension::getUserId,userId));
        if (extension == null){
            extension = new SyUserExtension();
            extension.setUserId(userId);
            save(extension);
        }
        return extension;
    }
}
