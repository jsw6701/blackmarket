package com.example.blackmarket.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.directory}")
    private String uploadDirectory;
    @Override
    public void uploadFiles(List<MultipartFile> files) {
        String absolutePath = System.getProperty("user.dir");
        for (MultipartFile file : files) {
            try {
                File folder = new File(uploadDirectory);
                // 저장할 위치의 디렉토리가 존지하지 않을 경우
                if(!folder.exists()){
                    // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
                    folder.mkdirs();
                }

                String fileName = file.getOriginalFilename();
                // 업로드된 파일을 서버의 특정 디렉토리에 저장
                String filePath = absolutePath + File.separator + uploadDirectory + File.separator + fileName;
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}