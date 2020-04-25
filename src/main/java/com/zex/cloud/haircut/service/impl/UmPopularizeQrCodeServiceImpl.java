package com.zex.cloud.haircut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zex.cloud.haircut.apis.WxApi;
import com.zex.cloud.haircut.dto.QrCodeRequest;
import com.zex.cloud.haircut.entity.UmPopularizeQrCode;
import com.zex.cloud.haircut.enums.ClientType;
import com.zex.cloud.haircut.enums.PopularizeType;
import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.mapper.UmPopularizeQrCodeMapper;
import com.zex.cloud.haircut.security.RequestUser;
import com.zex.cloud.haircut.service.IOAuthService;
import com.zex.cloud.haircut.service.IUmPopularizeQrCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zex.cloud.haircut.service.IUploadService;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-03-06
 */
@Service
public class UmPopularizeQrCodeServiceImpl extends ServiceImpl<UmPopularizeQrCodeMapper, UmPopularizeQrCode> implements IUmPopularizeQrCodeService {
    @Autowired
    private Retrofit wxRetrofit;
    @Autowired
    private IOAuthService ioAuthService;

    @Autowired
    private IUploadService iUploadService;

    @Override
    public UmPopularizeQrCode getQrCode(RequestUser user) {
        Long targetId = user.getClientType() == ClientType.SHOP_CLIENT ? user.getShopId() : user.getId();
        PopularizeType type = user.getClientType() == ClientType.SHOP_CLIENT ? PopularizeType.SHOP : PopularizeType.USER;
        UmPopularizeQrCode qrCode = getOne(new LambdaQueryWrapper<UmPopularizeQrCode>()
                .eq(UmPopularizeQrCode::getTargetId, targetId)
                .eq(UmPopularizeQrCode::getType, type));
        if (qrCode == null) {
            qrCode = new UmPopularizeQrCode();
            qrCode.setTargetId(targetId);
            qrCode.setType(type);
            String codeUrl = generateQrCode(targetId, type);
            qrCode.setQrCode(codeUrl);
            save(qrCode);
        }
        return qrCode;
    }

    private String generateQrCode(Long targetId, PopularizeType type) {
        QrCodeRequest qrCodeRequest = new QrCodeRequest();
        String scene = "id="+targetId+"&"+"type="+type.name();
        qrCodeRequest.setPage(null);
        qrCodeRequest.setScene(scene);
        Call<ResponseBody> call = wxRetrofit.create(WxApi.class).getQrCode(ioAuthService.getBackendAccessToken(), qrCodeRequest);
        try {
            Response<ResponseBody> execute = call.execute();
            if (execute.isSuccessful() && execute.body() != null) {
                return iUploadService.saveFile(null, execute.body().byteStream());
            } else {
                throw new ServerException("调用微信网络异常");
            }
        } catch (IOException e) {
            throw new ServerException("调用微信IO异常");

        }
    }
}
