package com.grapheople.miner.domain.webcrawler;

import com.grapheople.miner.domain.crawler.service.impl.InstagramScrapService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class WebCrawlerServiceTest {

    @Test
    public void test() {
        InstagramScrapService instagramScrapService = new InstagramScrapService();
        instagramScrapService.scrapContents("seongin_jee");
    }
}
