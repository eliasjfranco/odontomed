package com.odontomed.controller;

import com.odontomed.service.Interface.IAmazon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class AmazonS3Controller {

    @Autowired
    private IAmazon service;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file")MultipartFile file){
        service.uploadFile(file);
        return ResponseEntity.ok("Guardado");
    }

    @GetMapping
    public ResponseEntity<List<String>> getObjects(){
        return new ResponseEntity<List<String>>(service.getObjectsFromS3(), HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("key") String key){
        InputStreamResource resource = new InputStreamResource(service.downloadFile(key));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+key+"\"").body(resource);
    }

    @GetMapping("/carousel")
    public ResponseEntity<?> getImgsCarousel(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getImgs());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }




}
