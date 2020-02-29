package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.exception.ServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/upload")
@Api(tags = "文件上传相关接口")
public class UploadController {

    @Value("${files-location}")
    public String filesLocation;

    @PostMapping
    @ApiOperation("上传文件")
    public String upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            throw new ServerException();
        }
        String fileName = file.getOriginalFilename();  // 文件名
        if (StringUtils.isBlank(fileName)) {
            throw new ServerException();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //文件存储路径
        String realPath = filesLocation;
        String format = sdf.format(new Date());
        File parentFile = new File(realPath + format);
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            boolean ok = parentFile.mkdirs();
            if (!ok) {
                throw new ServerException();
            }
        }
        //文件存储的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        //新存储的文件名
        fileName = UUID.randomUUID().toString() + suffixName;

        File newFile = new File(parentFile.getAbsolutePath() + File.separator + fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new ServerException();
        }
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/files/" + format + "/" + fileName;

    }


}
