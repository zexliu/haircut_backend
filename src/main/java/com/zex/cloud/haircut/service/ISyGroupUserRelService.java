package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.SyGroupUserRel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-16
 */
public interface ISyGroupUserRelService extends IService<SyGroupUserRel> {
    void updateRelations(Long id, List<Long> groupIds);

    void removeByGroupId(Long id);

    List<Long> getGroupIdsByUserId(Long id);
}
