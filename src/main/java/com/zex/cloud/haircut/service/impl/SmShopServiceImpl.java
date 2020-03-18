package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.config.Constants;
import com.zex.cloud.haircut.dto.BrokenLinePoint;
import com.zex.cloud.haircut.enums.CollectType;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.vo.*;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.ShopWorkStatus;
import com.zex.cloud.haircut.exception.ExistsException;
import com.zex.cloud.haircut.mapper.SmShopMapper;
import com.zex.cloud.haircut.params.SmShopCurrentParam;
import com.zex.cloud.haircut.params.SmShopParam;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-19
 */
@Service
public class SmShopServiceImpl extends ServiceImpl<SmShopMapper, SmShop> implements ISmShopService {

    @Autowired
    private ISmShopServiceRelationService iSmShopServiceRelationService;

    @Autowired
    private ISyUserRoleRelService iSyUserRoleRelService;
    @Autowired
    private ISyGroupUserRelService iSyGroupUserRelService;
    @Autowired
    private ISmHalfTimeService iSmHalfTimeService;

    @Autowired
    private IHmStylistServiceRelationService iHmStylistServiceRelationService;
    @Autowired
    private IOmCommentScoreService iOmCommentScoreService;

    @Autowired
    private IHmStylistService iHmStylistService;
    @Autowired
    private IOmCommentService iOmCommentService;

    @Autowired
    private IUmUserCollectService iUmUserCollectService;
    @Override
    public IPage<SmShop> list(Page<SmShop> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude) {
        return baseMapper.list(convert, keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
    }

    @Override
    @Transactional
    public SmShop customSave(SmShop smShop) {
        valid(null, smShop.getUserId());
        // 保存角色信息
        iSyGroupUserRelService.save(Constants.SHOP_ADMIN_GROUP_ID, smShop.getUserId());
        //保存店铺
        save(smShop);
        return smShop;
    }


    @Override
    public SmShop updateCurrent(Long id, SmShopCurrentParam param) {
        SmShop smShop = new SmShop();
        BeanUtils.copyProperties(param, smShop);
        smShop.setId(id);
        updateById(smShop);
        //修改自己的时候增加的参数
        iSmShopServiceRelationService.updateRelations(id, param.getServiceIds());
        return smShop;
    }

    @Override
    public SmShop update(Long id, SmShopParam param) {
        SmShop smShop = getById(id);
        if (!param.getUserId().equals(smShop.getUserId())) {
            valid(id, param.getUserId());
            //移除旧用户的角色信息
            iSyGroupUserRelService.remove(Constants.SHOP_ADMIN_GROUP_ID, smShop.getUserId());
            iSyUserRoleRelService.save(Constants.SHOP_ADMIN_ROLE_ID, param.getUserId());
        }
        smShop.setId(id);
        BeanUtils.copyProperties(param, smShop);
        updateById(smShop);
        return smShop;
    }

    @Override
    public SmShop currentWorkStatus(Long id, ShopWorkStatus workStatus) {
        SmShop smShop = new SmShop();
        smShop.setId(id);
        smShop.setWorkStatus(workStatus);
        updateById(smShop);
        return smShop;
    }

    @Override
    public void updateTitle(Long id, List<Long> titleIds) {
        iSmShopServiceRelationService.updateRelations(id, titleIds);
    }

//    @Override
//    public void updateHalfTime(Long shopId, List<SmHalfTimeParam> params) {
//        iSmHalfTimeService.updateRelations(shopId, params);
//    }

    @Override
    public Long getShopIdByUserId(Long id) {
        return baseMapper.getShopIdByUserId(id);
    }

    @Override
    public List<BrokenLinePoint> brokenLines(LocalDate startAt, LocalDate endAt) {
        return baseMapper.brokenLines(startAt, endAt);
    }

    @Override
    public int count(LocalDate startAt, LocalDate endAt) {
        return baseMapper.count(startAt, endAt);
    }

    @Override
    public SmShopVO getByShopId(Long shopId) {
        SmShop smShop = getById(shopId);
        SmShopVO adminVO = new SmShopVO();
        BeanUtils.copyProperties(smShop, adminVO);
        List<Long> serviceIds = iSmShopServiceRelationService.getServiceIdsByShopId(shopId);
        adminVO.setServiceIds(serviceIds);
        return adminVO;
    }

    @Override
    public IPage<SmHomeShopVO> homeVo(Page<SmHomeShopVO> convert, String keywords, ShopWorkStatus workStatus, String provinceCode, String cityCode, String districtCode, Double longitude, Double latitude) {
        IPage<SmHomeShopVO> smHomeShopVOIPage = baseMapper.homeVo(convert, keywords, workStatus, provinceCode, cityCode, districtCode, longitude, latitude);
        for (SmHomeShopVO record : smHomeShopVOIPage.getRecords()) {
            //获取好评率
            Float rate = iOmCommentScoreService.getRate(record.getId());
            record.setScoreRate(rate);
            //获取最低的洗剪吹价格
            BigDecimal minAmount = iHmStylistServiceRelationService.getMinAmount(Constants.WASH_CUT_BLOW_ID, record.getId(), null);
            record.setPrice(minAmount);
            //是否在半价时间内
            Boolean halfStatus = iSmHalfTimeService.valid(record.getId(), LocalDateTime.now());
            record.setHalfStatus(halfStatus);
        }
        return smHomeShopVOIPage;

    }

    @Override
    public ShopDetailVO detail(Long id, Long userId) {
        ShopDetailVO detailVO = new ShopDetailVO();
        SmShop smShop = getById(id);
        BeanUtils.copyProperties(smShop,detailVO);
        ScoreCountVO scoreCountVO = iOmCommentService.getScoreCountVo(id,null);
        detailVO.setCommentCount(scoreCountVO.getCount());
        Page<OmOrderCommentInfo> page = new Page<>(1,3);
        List<OmOrderCommentInfo> commentVo = iOmCommentService.orderCommentPage(page,id,null,null,null).getRecords();
        detailVO.setComments(commentVo);
        int fansCount = iUmUserCollectService.shopCount(CollectType.SHOP,id);
        detailVO.setFansCount(fansCount);
        detailVO.setHalfStatus(iSmHalfTimeService.valid(id,LocalDateTime.now()));
//        Float score = iOmCommentScoreService.score(id);
        detailVO.setScore(scoreCountVO.getScore());
        List<Long> titleIds = iSmShopServiceRelationService.getServiceIdsByShopId(id);
        detailVO.setTitleIds(titleIds);
        List<HmStylistVO> stylistVOS = iHmStylistService.getStylistVoByShopId(id);
        detailVO.setStylists(stylistVOS);
        boolean collect = iUmUserCollectService.isCollect(userId, detailVO.getId(), CollectType.SHOP);
        detailVO.setCollect(collect);
        return detailVO;
    }

    @Override
    public void updateHalfStatus(Long shopId, Boolean halfStatus) {
        SmShop smShop = new SmShop();
        smShop.setHalfStatus(halfStatus);
        smShop.setId(shopId);
        updateById(smShop);
    }

    @Override
    public IPage<SmHomeShopVO> getCollectShops(Page<SmHomeShopVO> page, Long userId, Double latitude, Double longitude) {
        IPage<SmHomeShopVO> smHomeShopVOIPage = baseMapper.getCollectShops(page, userId, latitude, longitude);
        for (SmHomeShopVO record : smHomeShopVOIPage.getRecords()) {
            //获取好评率
            Float rate = iOmCommentScoreService.getRate(record.getId());
            record.setScoreRate(rate);
            //获取最低的洗剪吹价格
            BigDecimal minAmount = iHmStylistServiceRelationService.getMinAmount(Constants.WASH_CUT_BLOW_ID, record.getId(), null);
            record.setPrice(minAmount);
            //是否在半价时间内
            Boolean halfStatus = iSmHalfTimeService.valid(record.getId(), LocalDateTime.now());
            record.setHalfStatus(halfStatus);
        }
        return smHomeShopVOIPage;
    }

    private void valid(Long id, Long userId) {
        int count = count(new LambdaQueryWrapper<SmShop>().eq(SmShop::getUserId, userId)
                .ne(id != null, SmShop::getId, id));
        if (count > 0) {
            throw new ExistsException("该用户已经拥有一个店铺了");
        }

    }
}
