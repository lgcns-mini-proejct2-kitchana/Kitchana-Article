package com.kitcha.article.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class NaverApiClient {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 공통 뉴스 검색 메서드 (키워드, 랜덤)
    public JsonNode fetchNews(String query, int display) {
        String url = "https://openapi.naver.com/v1/search/news.json?query=" + query + "&display=" + display;

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 및 응답
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        try {
            return objectMapper.readTree(response.getBody()).path("items");
        } catch (Exception e) {
            throw new RuntimeException("Naver API 호출 실패: " + e.getMessage(), e);
        }
    }
}
