package com.zex.cloud.haircut.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public interface IUploadService {
    String saveFile(String fileName, InputStream byteArrayInputStream);
}
