package com.odontomed.service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IAmazon {

    void uploadFile(MultipartFile file);

    List<String> getObjectsFromS3();

    InputStream downloadFile(String key);

    List<String> getImgs();
}
