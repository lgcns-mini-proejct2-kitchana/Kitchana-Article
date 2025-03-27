package com.kitcha.article.service;

import com.kitcha.article.dto.response.MyPickNewsResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadNewsService {
    List<MyPickNewsResponseDto> processUploadedImage(MultipartFile file);
}
