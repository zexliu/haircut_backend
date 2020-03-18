package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmDomain;
import com.zex.cloud.haircut.entity.HmStylistDomainRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmStylistDomainRelationService extends IService<HmStylistDomainRelation> {

    void removeByStylistId(Long id);

    void removeByDomainId(Long id);

    void updateRelations(Long id, List<Long> domainIds);

    List<HmDomain> getByStylistId(Long id);
}
