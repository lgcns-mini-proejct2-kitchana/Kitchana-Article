package com.kitcha.article.service;

import com.kitcha.article.dto.response.MyPickNewsResponseDto;

import java.util.List;

public interface MyPickNewsService {
    List<MyPickNewsResponseDto> getMyPickNews(String interest);
}
