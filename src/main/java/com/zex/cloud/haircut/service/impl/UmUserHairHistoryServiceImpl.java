package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.entity.UmUserHairHistory;
import com.zex.cloud.haircut.mapper.UmUserHairHistoryMapper;
import com.zex.cloud.haircut.params.UmUserHairHistoryParam;
import com.zex.cloud.haircut.service.IUmUserHairHistoryService;
import com.zex.cloud.haircut.vo.UmUserHairHistoryDate;
import org.springframework.beans.BeanUtils;
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
public class UmUserHairHistoryServiceImpl extends ServiceImpl<UmUserHairHistoryMapper, UmUserHairHistory> implements IUmUserHairHistoryService {

    @Override
    public UmUserHairHistory create(UmUserHairHistoryParam param, Long userId) {
        UmUserHairHistory history  = new UmUserHairHistory();
        BeanUtils.copyProperties(param,history);
        history.setUserId(userId);
        save(history);
        return history;
    }

    @Override
    public UmUserHairHistory update(Long id, UmUserHairHistoryParam param, Long userId) {
        UmUserHairHistory history  = new UmUserHairHistory();
        BeanUtils.copyProperties(param,history);
        history.setUserId(userId);
        history.setId(id);
        update(history,new LambdaUpdateWrapper<UmUserHairHistory>()
        .eq(UmUserHairHistory::getUserId,userId)
        .eq(UmUserHairHistory::getId,id));
        return history;
    }

    @Override
    public void delete(Long id, Long userId) {
        remove(new LambdaUpdateWrapper<UmUserHairHistory>().eq(UmUserHairHistory::getUserId,userId)
        .eq(UmUserHairHistory::getId,id));
    }

    @Override
    public IPage<UmUserHairHistoryDate> datePage(Page<UmUserHairHistoryDate> page, Long userId) {
        return baseMapper.datePage(page,userId);
    }
}
