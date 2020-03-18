package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.entity.OmFlower;
import com.zex.cloud.haircut.entity.OmOrder;
import com.zex.cloud.haircut.enums.FlowerType;
import com.zex.cloud.haircut.mapper.OmFlowerMapper;
import com.zex.cloud.haircut.service.IOmFlowerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.util.DecimalUtils;
import com.zex.cloud.haircut.vo.OmFlowerVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-23
 */
@Service
public class OmFlowerServiceImpl extends ServiceImpl<OmFlowerMapper, OmFlower> implements IOmFlowerService {

    @Override
    public void onPayHook(OmOrder omOrder) {
        OmFlower omFlower = new OmFlower();
        omFlower.setCount(omOrder.getAmount().intValue());
        omFlower.setDescription("购买鲜花");
        omFlower.setUserId(omOrder.getUserId());
        omFlower.setOrderId(omOrder.getId());
        omFlower.setType(FlowerType.BUY);
        save(omFlower);
    }

    @Override
    public IPage<OmFlowerVo> flowerVoPage(Page<OmFlowerVo> page, FlowerType type, Long userId, Long shopId, LocalDateTime startAt, LocalDateTime endAt) {
        return baseMapper.flowerVOPage(page,userId,shopId,type,startAt,endAt);
    }


}
