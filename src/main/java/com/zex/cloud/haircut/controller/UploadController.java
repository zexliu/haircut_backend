package com.zex.cloud.haircut.controller;

import com.zex.cloud.haircut.exception.ServerException;
import com.zex.cloud.haircut.service.IUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/upload")
@Api(tags = "文件上传相关接口")
public class UploadController {


    @Autowired
    private IUploadService iUploadService;

    @PostMapping
    @ApiOperation("上传文件")
    public String upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {

        if (file.isEmpty()) {
            throw new ServerException();
        }
        String fileName = file.getOriginalFilename();  // 文件名
        try {
            return iUploadService.saveFile(fileName,new ByteArrayInputStream(file.getBytes()));
        } catch (IOException e) {
            throw new ServerException();
        }
    }


}
