package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zex.cloud.haircut.config.Constants;
import com.zex.cloud.haircut.entity.*;
import com.zex.cloud.haircut.enums.CollectType;
import com.zex.cloud.haircut.enums.GenderType;
import com.zex.cloud.haircut.exception.ForbiddenException;
import com.zex.cloud.haircut.mapper.HmStylistMapper;
import com.zex.cloud.haircut.params.HmStylistParam;
import com.zex.cloud.haircut.params.HmStylistServiceParam;
import com.zex.cloud.haircut.params.SmShopGrouponParam;
import com.zex.cloud.haircut.response.OmOrderCommentInfo;
import com.zex.cloud.haircut.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-02-20
 */
@Service
public class HmStylistServiceImpl extends ServiceImpl<HmStylistMapper, HmStylist> implements IHmStylistService {

    @Autowired
    private IHmStylistDomainRelationService iHmStylistDomainRelationService;

    @Autowired
    private IHmWorkCaseService iHmWorkCaseService;

    @Autowired
    private IHmStylistServiceRelationService iHmStylistServiceRelationService;

    @Autowired
    private IHmStylistResumeService iHmStylistResumeService;

    @Autowired
    private ISmShopGrouponService iSmShopGrouponService;

    @Autowired
    private IHmServiceService iHmServiceService;

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmCommentScoreService iOmCommentScoreService;
    @Autowired
    private IOmCommentService iOmCommentService;

    @Autowired
    private ISmHalfTimeService iSmHalfTimeService;

    @Autowired
    private IUmUserCollectService iUmUserCollectService;


    @Autowired
    private ISmShopDiscountService iSmShopDiscountService;
    @Override
    @Transactional
    public HmStylist save(HmStylistParam param, Long shopId) {
        HmStylist stylist = new HmStylist();
        BeanUtils.copyProperties(param, stylist);
        stylist.setDeleteStatus(false);
        stylist.setShopId(shopId);
        save(stylist);
        //更新擅长领域关联关系
        iHmStylistDomainRelationService.updateRelations(stylist.getId(), param.getDomainIds());

        //更新履历
        iHmStylistResumeService.updateResumes(stylist.getId(), param.getResumes());

        iHmWorkCaseService.updateWorkCases(stylist.getId(),shopId,param.getWorkCases());

        List<HmStylistServiceParam> services = new ArrayList<>();

        List<SmShopGrouponParam> groupons = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(param.getMales())) {
            for (HmServiceGrouponVo male : param.getMales()) {
                if (male.getSinglePrice() != null){
                    HmStylistServiceParam serviceParam = new HmStylistServiceParam();
                    serviceParam.setMalePrice(male.getSinglePrice());
                    serviceParam.setServiceId(male.getServiceId());
                    services.add(serviceParam);
                }
                if (male.getGrouponPrice() != null){
                    SmShopGrouponParam smShopGrouponParam = new SmShopGrouponParam();
                    smShopGrouponParam.setAmount(male.getGrouponPrice());
                    smShopGrouponParam.setCount(3);
                    smShopGrouponParam.setGender(GenderType.MALE);
                    smShopGrouponParam.setServiceId(male.getServiceId());
                    groupons.add(smShopGrouponParam);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(param.getFemales())) {
            for (HmServiceGrouponVo female : param.getFemales()) {
                if (female.getSinglePrice() != null){
                    HmStylistServiceParam serviceParam = null;
                    for (HmStylistServiceParam service : services) {
                        if (service.getServiceId().equals(female.getServiceId())){
                            serviceParam = service;
                        }
                    }
                    if (serviceParam == null){
                         serviceParam = new HmStylistServiceParam();
                        serviceParam.setFemalePrice(female.getSinglePrice());
                        serviceParam.setServiceId(female.getServiceId());
                        services.add(serviceParam);
                    }else {
                        serviceParam.setFemalePrice(female.getSinglePrice());
                    }

                }
                if (female.getGrouponPrice() != null){
                    SmShopGrouponParam smShopGrouponParam = new SmShopGrouponParam();
                    smShopGrouponParam.setAmount(female.getGrouponPrice());
                    smShopGrouponParam.setCount(3);
                    smShopGrouponParam.setGender(GenderType.FEMALE);
                    smShopGrouponParam.setServiceId(female.getServiceId());
                    groupons.add(smShopGrouponParam);
                }
            }
        }
        //更新服务价格关联关系
        iHmStylistServiceRelationService.updateRelations(stylist.getId(),services);

        iSmShopGrouponService.updateGrouponsByStylist(stylist.getId(), shopId, groupons);

        return stylist;
    }

    @Override
    @Transactional
    public HmStylist update(Long id, HmStylistParam param, Long shopId) {
        HmStylist stylist = getById(id);
        if (stylist == null || !stylist.getShopId().equals(shopId)) {
            throw new ForbiddenException();
        }
        BeanUtils.copyProperties(param, stylist);
        stylist.setId(id);
        updateById(stylist);
        //更新擅长领域关联关系
        iHmStylistDomainRelationService.updateRelations(stylist.getId(), param.getDomainIds());

        //更新履历
        iHmStylistResumeService.updateResumes(stylist.getId(), param.getResumes());

        iHmWorkCaseService.updateWorkCases(stylist.getId(),shopId,param.getWorkCases());

        List<HmStylistServiceParam> services = new ArrayList<>();

        List<SmShopGrouponParam> groupons = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(param.getMales())) {
            for (HmServiceGrouponVo male : param.getMales()) {
                if (male.getSinglePrice() != null){
                    HmStylistServiceParam serviceParam = new HmStylistServiceParam();
                    serviceParam.setMalePrice(male.getSinglePrice());
                    serviceParam.setServiceId(male.getServiceId());
                    services.add(serviceParam);
                }
                if (male.getGrouponPrice() != null){
                    SmShopGrouponParam smShopGrouponParam = new SmShopGrouponParam();
                    smShopGrouponParam.setAmount(male.getGrouponPrice());
                    smShopGrouponParam.setCount(3);
                    smShopGrouponParam.setGender(GenderType.MALE);
                    smShopGrouponParam.setServiceId(male.getServiceId());
                    groupons.add(smShopGrouponParam);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(param.getFemales())) {
            for (HmServiceGrouponVo female : param.getFemales()) {
                if (female.getSinglePrice() != null){
                    HmStylistServiceParam serviceParam = null;
                    for (HmStylistServiceParam service : services) {
                        if (service.getServiceId().equals(female.getServiceId())){
                            serviceParam = service;
                        }
                    }
                    if (serviceParam == null){
                        serviceParam = new HmStylistServiceParam();
                        serviceParam.setFemalePrice(female.getSinglePrice());
                        serviceParam.setServiceId(female.getServiceId());
                        services.add(serviceParam);
                    }else {
                        serviceParam.setFemalePrice(female.getSinglePrice());
                    }

                }
                if (female.getGrouponPrice() != null){
                    SmShopGrouponParam smShopGrouponParam = new SmShopGrouponParam();
                    smShopGrouponParam.setAmount(female.getGrouponPrice());
                    smShopGrouponParam.setCount(3);
                    smShopGrouponParam.setGender(GenderType.FEMALE);
                    smShopGrouponParam.setServiceId(female.getServiceId());
                    groupons.add(smShopGrouponParam);
                }
            }
        }

        iSmShopGrouponService.updateGrouponsByStylist(stylist.getId(), shopId,groupons);

        //更新服务价格关联关系
        iHmStylistServiceRelationService.updateRelations(stylist.getId(), services);
        return stylist;
    }

    @Override
    @Transactional
    public void delete(Long id, Long shopId) {
        HmStylist stylist = getById(id);
        if (stylist == null || !stylist.getShopId().equals(shopId)) {
            throw new ForbiddenException();
        }
        removeById(id);
        //删除关联的擅长领域
        iHmStylistDomainRelationService.removeByStylistId(id);
        //删除关联的服务项目
        iHmStylistServiceRelationService.removeByStylistId(id);
        //删除关联的案例作品
        iHmWorkCaseService.removeByStylistId(id);

        //删除关联履历
        iHmStylistResumeService.removeByStylist(id);

        //删除团购
        iSmShopGrouponService.removeByStylistId(id);
    }

    @Override
    public HmStylistDetail getDetailById(Long id) {
        HmStylistDetail hmStylistDetail = new HmStylistDetail();
        HmStylist stylist = getById(id);
        BeanUtils.copyProperties(stylist, hmStylistDetail);
        List<HmDomain> domains = iHmStylistDomainRelationService.getByStylistId(id);
        hmStylistDetail.setDomains(domains);

        List<HmWorkCase> workCases = iHmWorkCaseService.getByStylistId(id);
        hmStylistDetail.setWorkCases(workCases);
        List<HmStylistResume> resumes = iHmStylistResumeService.getByStylistId(id);
        hmStylistDetail.setResumes(resumes);
        List<SmShopGroupon> groupons = iSmShopGrouponService.getByStylistId(id);
//        hmStylistDetail.setGroupons(groupons);
        List<HmStylistServiceRelation> services = iHmStylistServiceRelationService.getByStylistId(id);
//        hmStylistDetail.setServices(services);

        List<SmShopDiscount> smShopDiscounts = iSmShopDiscountService.getByShopId(stylist.getShopId());
        List<HmService> hmServices = iHmServiceService.list();
        List<HmServiceGrouponVo> males = new ArrayList<>();
        List<HmServiceGrouponVo> females = new ArrayList<>();
        for (HmService hmService : hmServices) {
            SmShopDiscount discount =  findDiscount(hmService.getId(),smShopDiscounts);
            SmShopGroupon maleGroupon = findGroupon(GenderType.MALE, hmService.getId(), groupons);
            SmShopGroupon femaleGroupon = findGroupon(GenderType.FEMALE, hmService.getId(), groupons);
            HmStylistServiceRelation relation = findServiceRelatino(hmService.getId(), services);
            HmServiceGrouponVo maleVo = new HmServiceGrouponVo();
            HmServiceGrouponVo femaleVo = new HmServiceGrouponVo();
            maleVo.setServiceId(hmService.getId());
            maleVo.setGrouponId(maleGroupon != null ? maleGroupon.getId() : null);
            maleVo.setGrouponPrice(maleGroupon != null ? maleGroupon.getAmount() : null);
            maleVo.setSinglePrice(relation != null ? relation.getMalePrice() : null);
            maleVo.setStylistServiceId(relation != null ? relation.getId() : null);
            maleVo.setServiceName(hmService.getName());
            maleVo.setDiscount(discount != null? discount.getDiscount(): null);
            maleVo.setDiscountId(discount !=  null? discount.getId(): null);
            if (maleVo.getSinglePrice() != null){
                males.add(maleVo);
            }

            femaleVo.setServiceId(hmService.getId());
            femaleVo.setGrouponId(femaleGroupon != null ? femaleGroupon.getId() : null);
            femaleVo.setGrouponPrice(femaleGroupon != null ? femaleGroupon.getAmount() : null);
            femaleVo.setSinglePrice(relation != null ? relation.getFemalePrice() : null);
            femaleVo.setStylistServiceId(relation != null ? relation.getId() : null);
            femaleVo.setServiceName(hmService.getName());
            femaleVo.setDiscount(discount != null? discount.getDiscount(): null);
            femaleVo.setDiscountId(discount !=  null? discount.getId(): null);
            if (femaleVo.getSinglePrice() != null){
                females.add(femaleVo);
            }
        }
        hmStylistDetail.setFemales(females);
        hmStylistDetail.setMales(males);

        return hmStylistDetail;
    }


    @Override
    public List<HmStylistVO> getStylistVoByShopId(Long id) {
        List<HmStylistVO> stylistVOS = baseMapper.getHmStylistVOsBySHopId(id, Constants.WASH_CUT_BLOW_ID);
        for (HmStylistVO stylistVO : stylistVOS) {
            int waitCount  =  iOmShopOrderService.getWaitCount(null, stylistVO.getId(), LocalDateTime.now());
            //等待人数
            stylistVO.setWaitCount(waitCount);
            stylistVO.setWaitTime(waitCount * 30);
            //手艺值
            int commentCount = iOmCommentScoreService.stylistGoodCommentCount(id);
            int orderCount = iOmShopOrderService.stylistOrderCount(id);
            stylistVO.setSkillPraise(commentCount + orderCount);
        }
        return stylistVOS;
    }

    @Override
    public IPage<HmStylistCollectVO> getCollectStylist(Page<HmStylistCollectVO> page, Long userId, Double latitude, Double longitude) {
        IPage<HmStylistCollectVO> vos = baseMapper.getCollectStylists(page,userId,Constants.WASH_CUT_BLOW_ID,latitude,longitude);
        for (HmStylistCollectVO record : vos.getRecords()) {
            int waitCount  =  iOmShopOrderService.getWaitCount(null, record.getId(), LocalDateTime.now());
            //等待人数
            record.setWaitCount(waitCount);
            record.setWaitTime(waitCount * 30);
            //手艺值
            int commentCount = iOmCommentScoreService.stylistGoodCommentCount(record.getId());
            int orderCount = iOmShopOrderService.stylistOrderCount(record.getId());
            record.setSkillPraise(commentCount + orderCount);
        }

        return vos;
    }

    @Override
    public IPage<HmStylistCollectVO> list(Page<HmStylistCollectVO> page, String keywords, Double latitude, Double longitude) {
        IPage<HmStylistCollectVO> vos = baseMapper.list(page,keywords,Constants.WASH_CUT_BLOW_ID,latitude,longitude);
        for (HmStylistCollectVO record : vos.getRecords()) {
            int waitCount  =  iOmShopOrderService.getWaitCount(null, record.getId(), LocalDateTime.now());
            //等待人数
            record.setWaitCount(waitCount);
            record.setWaitTime(waitCount * 30);
            //手艺值
            int commentCount = iOmCommentScoreService.stylistGoodCommentCount(record.getId());
            int orderCount = iOmShopOrderService.stylistOrderCount(record.getId());
            record.setSkillPraise(commentCount + orderCount);
        }
        return vos;
    }


    @Override
    public HmStylistDetailVO detailVO(Long id, Double latitude, Double longitude, Long userId) {
        HmStylistDetailVO detail = baseMapper.getDetail(id,latitude,longitude,userId);
        List<HmWorkCase> workCases = iHmWorkCaseService.getByStylistId(id);
        detail.setWorkCases(workCases);
        List<HmStylistResume> resumes = iHmStylistResumeService.getByStylistId(id);
        detail.setResumes(resumes);
        int waitCount  =  iOmShopOrderService.getWaitCount(null, detail.getId(), LocalDateTime.now());
        //等待人数
        detail.setWaitCount(waitCount);
        detail.setWaitTime(waitCount * 30);
        Page<OmOrderCommentInfo> page = new Page<>(1,3);
        List<OmOrderCommentInfo> commentVo = iOmCommentService.orderCommentPage(page,null,id,null,null).getRecords();
        detail.setComments(commentVo);
        ScoreCountVO scoreCountVO = iOmCommentService.getScoreCountVo(null,id);
        detail.setCommentCount(scoreCountVO.getCount());
        detail.setScore(scoreCountVO.getScore());

        detail.setHalfStatus(iSmHalfTimeService.valid(id,LocalDateTime.now()));
        BigDecimal minAmount = iHmStylistServiceRelationService.getMinAmount(Constants.WASH_CUT_BLOW_ID, null, id);
        detail.setPrice(minAmount);
        boolean collect = iUmUserCollectService.isCollect(userId,id, CollectType.STYLIST);
        detail.setCollect(collect);
        return detail;
    }



    private HmStylistServiceRelation findServiceRelatino(Long id, List<HmStylistServiceRelation> services) {
        if (CollectionUtils.isEmpty(services)) {
            return null;
        }
        for (HmStylistServiceRelation item : services) {
            if (item.getServiceId().equals(id)) {
                return item;
            }
        }
        return null;

    }



    private SmShopDiscount findDiscount(Long id, List<SmShopDiscount> smShopDiscounts) {
        if (CollectionUtils.isEmpty(smShopDiscounts)) {
            return null;
        }
        for (SmShopDiscount item : smShopDiscounts) {
            if (item.getServiceId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    private SmShopGroupon findGroupon(GenderType genderType, Long id, List<SmShopGroupon> groupons) {
        if (CollectionUtils.isEmpty(groupons)) {
            return null;
        }
        for (SmShopGroupon item : groupons) {
            if (item.getServiceId().equals(id) && item.getGender() == genderType) {
                return item;
            }
        }
        return null;
    }
}
