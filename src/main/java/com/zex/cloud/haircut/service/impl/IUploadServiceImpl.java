package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.service.IUploadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class IUploadServiceImpl implements IUploadService {
    @Value("${files-location}")
    public String filesLocation;



    @Value("${host-url}")
    public String hostUrl;


    @Override
    public String saveFile(String fileName, InputStream inputStream) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        String suffixName = StringUtils.isNotBlank(fileName)? fileName.substring(fileName.lastIndexOf(".")) : ".jpg";
        fileName = UUID.randomUUID().toString() + suffixName;
        String parentFilePath = filesLocation +  File.separator + format;
        File parentFile = new File(parentFilePath);
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            boolean ok = parentFile.mkdirs();
            if (!ok) {
                throw new ServerException();
            }
        }
        try {
            IOUtils.copy(inputStream,new FileOutputStream(parentFilePath + File.separator + fileName));
        } catch (IOException e) {
            throw new ServerException("保存文件IO异常");
        }
        return hostUrl + File.separator + "files" + File.separator  + format + File.separator  + fileName;
    }
}
