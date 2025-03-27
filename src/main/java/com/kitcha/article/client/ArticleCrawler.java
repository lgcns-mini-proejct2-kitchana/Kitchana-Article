package com.kitcha.article.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class ArticleCrawler {
    public String getArticleContent(String articleUrl) {
        try {
            Document document = Jsoup.connect(articleUrl)
                    .userAgent("Mozilla/5.0")
                    .get();

            // p태그와 span태그만 추출
            Elements paragraphs = document.select("p, span");

            StringBuilder contentBuilder = new StringBuilder();
            for (Element element : paragraphs) {
                String text = element.text().trim();
                if (!text.isEmpty()) {
                    contentBuilder.append(text).append("\n");
                }
            }

            String content = contentBuilder.toString();
            return content.isEmpty() ? "본문을 찾을 수 없습니다." : content;

        } catch (Exception e) {
            e.printStackTrace();
            return "페이지 내용을 가져오는 데 실패했습니다.";
        }
    }
}
