package com.kitcha.article.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomNewsResponseDto {

    @JsonProperty("news_title")
    private String newsTitle;

    @JsonProperty("long_summary")
    private String longSummary;

    private String interest;       // 관심사
    private String keyword;        // 핵심 키워드 (검색용)
}
