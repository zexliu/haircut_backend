package com.zex.cloud.haircut.service;

import com.zex.cloud.haircut.entity.HmStylistServiceRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.params.HmStylistServiceParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
public interface IHmStylistServiceRelationService extends IService<HmStylistServiceRelation> {

    void removeByStylistId(Long id);

    void updateRelations(Long id, List<HmStylistServiceParam> services);

    BigDecimal getPriceByServiceIdStylistIdAndSex(Long serviceId, Long stylistId, GenderType genderType);


}
