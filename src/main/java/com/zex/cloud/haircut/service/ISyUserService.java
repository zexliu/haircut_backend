package com.zex.cloud.haircut.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.SyUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.params.PasswordCurrentParam;
import com.zex.cloud.haircut.params.PasswordParam;
import com.zex.cloud.haircut.params.SyUserParam;
import com.zex.cloud.haircut.response.SyUserDetail;
import com.zex.cloud.haircut.security.RequestUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyUserService extends IService<SyUser> {

    SyUser save(SyUserParam syUserParam, String operatorIp, Long operatorId);

    SyUser update(Long id, SyUserParam syUserParam, String operatorIp, Long operatorId);


    RequestUser getRequestUser(String username, String password);

   IPage<SyUser> list(String keywords, Boolean locked,Boolean enable,Long groupId, Page<SyUser> convert);

    SyUserDetail detail(Long id);

    void adminPassword(Long id, String password, String operatorIp, Long operatorId);

    void password(PasswordParam param);

    void passwordCurrent(PasswordCurrentParam param, Long id);

    RequestUser getRequestUser(Long userId);

}
