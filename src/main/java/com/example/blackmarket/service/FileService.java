package com.example.blackmarket.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void uploadFiles(List<MultipartFile> files);
}