package com.grapheople.miner.domain.webcrawler;

import com.grapheople.miner.domain.crawler.service.InstagramScrapService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
//@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles(value = "local")
public class WebCrawlerServiceTest {
//    @Autowired
//    private InstagramScrapService instagramScrapService;

    @Test
    public void test() {
        InstagramScrapService instagramScrapService = new InstagramScrapService();
        instagramScrapService.scrapContents("seongin_jee");
    }
}
