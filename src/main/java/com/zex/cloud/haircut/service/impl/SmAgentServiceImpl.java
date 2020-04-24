package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zex.cloud.haircut.config.Constants;
import com.zex.cloud.haircut.entity.SmAgent;
import com.zex.cloud.haircut.entity.SmAgentApply;
import com.zex.cloud.haircut.entity.SyUser;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SmAgentMapper;
import com.zex.cloud.haircut.params.SyUserParam;
import com.zex.cloud.haircut.service.ISmAgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.ISyUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-18
 */
@Service
public class SmAgentServiceImpl extends ServiceImpl<SmAgentMapper, SmAgent> implements ISmAgentService {

    @Autowired
    private ISyUserService iSyUserService;

    @Override
    public SmAgent customSave(SmAgent smAgent) {
        //创建账号
        SyUserParam syUserParam = new SyUserParam();
        SyUser clientUser = iSyUserService.getById(smAgent.getUserId());
        syUserParam.setAvatar(clientUser.getAvatar());
        syUserParam.setEmail(smAgent.getEmail());
        syUserParam.setEnable(true);
        syUserParam.setGender(clientUser.getGender());
        syUserParam.setLocked(false);
        syUserParam.setGroupIds(Lists.newArrayList(Constants.AGENT_GROUP_ID));
        syUserParam.setMobile(smAgent.getLinkMobile());
        syUserParam.setUsername(smAgent.getLinkMobile());
        String password = StringUtils.substring(smAgent.getLinkMobile(), smAgent.getLinkMobile().length() - 6);
        String md5 = DigestUtils.md5Hex(password);
        syUserParam.setNickname(clientUser.getNickname());
        syUserParam.setPassword(md5);
        SyUser syUser = iSyUserService.save(syUserParam,null,null);
        smAgent.setUserId(syUser.getId());
        valid(null, smAgent);
        //保存代理商
        save(smAgent);
        return smAgent;
    }

    @Override
    public SmAgent getByUserId(Long id) {
        return getOne(new LambdaQueryWrapper<SmAgent>().eq(SmAgent::getUserId,id));
    }

    @Override
    public SmAgent getByLocation(Integer provinceCode, Integer cityCode) {
        return getOne(new LambdaQueryWrapper<SmAgent>().eq(SmAgent::getProvinceCode,provinceCode).eq(SmAgent::getCityCode,cityCode));
    }

    @Override
    public void updateEnableStatus(Boolean enableStatus, Long id) {
        SmAgent smAgent = new SmAgent();
        smAgent.setId(id);
        smAgent.setEnable(enableStatus);
        updateById(smAgent);
    }

    private void valid(Long id, SmAgent agent) {
        SmAgent smAgent = getOne(new LambdaQueryWrapper<SmAgent>()
        .eq(SmAgent::getProvinceCode,agent.getProvinceCode())
        .eq(SmAgent::getCityCode,agent.getCityCode())
        .ne(id != null,SmAgent::getId,id));
        if (smAgent != null){
            throw new ExistsException("该地区已有代理商");
        }
    }
}
