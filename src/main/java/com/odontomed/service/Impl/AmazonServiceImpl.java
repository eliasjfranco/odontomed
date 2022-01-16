package com.odontomed.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.odontomed.service.Interface.IAmazon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmazonServiceImpl implements IAmazon {

    private static final Logger logger = LoggerFactory.getLogger(AmazonServiceImpl.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucket;

    @Override
    public void uploadFile(MultipartFile file) {
        File mainFile = new File(file.getOriginalFilename());
        try(FileOutputStream stream = new FileOutputStream(mainFile)){
            stream.write(file.getBytes());
            String fileName = System.currentTimeMillis() + "_" + mainFile.getName();
            PutObjectRequest request = new PutObjectRequest(bucket, fileName, mainFile);
            amazonS3.putObject(request);
        }catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getObjectsFromS3() {
        ListObjectsV2Result result = amazonS3.listObjectsV2(bucket);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> list = objects.stream().map(item -> {
            return item.getKey();
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public InputStream downloadFile(String key) {
        S3Object object = amazonS3.getObject(bucket, key);
        return object.getObjectContent();
    }

    @Override
    public List<String> getImgs() {
        ListObjectsV2Result result = amazonS3.listObjectsV2("https://odontomed.s3.sa-east-1.amazonaws.com/banner/");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> list = objects.stream().map(item -> {
            return item.getKey();
        }).collect(Collectors.toList());
        return list;
    }
}
