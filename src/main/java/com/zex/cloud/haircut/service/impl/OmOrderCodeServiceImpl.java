package com.zex.cloud.haircut.service.impl;

import com.google.zxing.WriterException;
import com.zex.cloud.haircut.dto.OrderCode;
import com.zex.cloud.haircut.entity.SmShop;
import com.zex.cloud.haircut.enums.OrderCodeType;
import com.zex.cloud.haircut.exception.NotFoundException;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.response.SimpleResp;
import com.zex.cloud.haircut.service.IOmOrderCodeService;
import com.zex.cloud.haircut.service.IOmShopOrderService;
import com.zex.cloud.haircut.service.IOmUserGrouponService;
import com.zex.cloud.haircut.service.ISmShopService;
import com.zex.cloud.haircut.util.RedisKeys;
import com.zex.cloud.haircut.util.ZXingUtil;
import com.zex.cloud.haircut.vo.ShopCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class OmOrderCodeServiceImpl implements IOmOrderCodeService {
    //获取核销码
    @Value("${files-location}")
    public String filesLocation;


    @Value("${host-url}")
    public String hostUrl;

    @Autowired
    private IOmShopOrderService iOmShopOrderService;

    @Autowired
    private IOmUserGrouponService iOmUserGrouponService;

    @Autowired
    private ISmShopService iSmShopService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public ShopCodeVO getCode(Long id, OrderCodeType type, Long userId) throws IOException, WriterException {

        Long shopId = null;
        if (type == OrderCodeType.SERVICE){
            shopId =  iOmShopOrderService.validUseStatus(id,userId);
        }else if (type == OrderCodeType.GROUPON){
            shopId = iOmUserGrouponService.validUseStatus(id,userId);
        }
        ShopCodeVO vo = new ShopCodeVO();
        SmShop shop = iSmShopService.getById(shopId);
        vo.setAddress(shop.getAddress());
        vo.setMobile(shop.getLeaderMobile());
        vo.setName(shop.getName());
        String qrCode = "LKI@#!$DOPOC*#PC#PX<SDF>" + UUID.randomUUID().toString();
        OrderCode orderCode = new OrderCode();
        orderCode.setType(type);
        orderCode.setId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String parentFilePath = filesLocation +  File.separator + format;
        File parentFile = new File(parentFilePath);
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            boolean ok = parentFile.mkdirs();
            if (!ok) {
                throw new ServerException();
            }
        }
        String imageName =  UUID.randomUUID().toString()+".JPEG";
        ZXingUtil.createQrCode(new FileOutputStream(parentFilePath+ File.separator +imageName),qrCode,900,"JPEG");

        redisTemplate.opsForValue().set(RedisKeys.orderCodeKey(qrCode),orderCode,1, TimeUnit.DAYS);
        String url = hostUrl + File.separator + "files" + File.separator  + format + File.separator  + imageName;
        vo.setQrCodeUrl(url);
        return vo;
    }

    @Override
    public String use(String code, Long shopId) {
        OrderCode orderCode = (OrderCode) redisTemplate.opsForValue().get(RedisKeys.orderCodeKey(code));
        if (orderCode == null){
            throw new NotFoundException("核销码不存在,或已过期");
        }
        if (orderCode.getType() == OrderCodeType.SERVICE){
            iOmShopOrderService.use(orderCode.getId(),shopId);
        }else if (orderCode.getType() == OrderCodeType.GROUPON){
            iOmUserGrouponService.use(orderCode.getId(),shopId);
        }
        return SimpleResp.SUCCESS;
    }


}
