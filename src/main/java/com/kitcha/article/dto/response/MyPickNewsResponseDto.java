package com.kitcha.article.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class MyPickNewsResponseDto {

    @JsonProperty("news_title")
    private String newsTitle;      // 기사 제목

    @JsonProperty("news_date")
    private String newsDate;       // 기사 날짜

    @JsonProperty("news_url")
    private String newsUrl;        // 기사 URL

    @JsonProperty("short_summary")
    private String shortSummary;   // 짧은 요약

    @JsonProperty("long_summary")
    private String longSummary;    // 상세 요약
}
