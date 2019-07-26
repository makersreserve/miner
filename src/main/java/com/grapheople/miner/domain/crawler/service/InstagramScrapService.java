package com.grapheople.miner.domain.crawler.service;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class InstagramScrapService {
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String DRIVER_DIR = PROJECT_DIR + "/drivers/geckodriver";
    private static final String INSTAGRAM_DOMAIN = "https://www.instagram.com/";

    public InstagramScrapService() {
        System.setProperty("webdriver.gecko.driver", DRIVER_DIR);
    }

    private int getArticleCount(WebDriver driver) {
        try {
            WebElement articleCountElement = driver.findElement(By.xpath(".//span[contains(@class, 'g47SY')][1]"));
            return Integer.parseInt(articleCountElement.getText());
        } catch (Exception e) {
            log.error("can not find article count");
            return 0;
        }
    }

    private void scrollToBottom(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    private void clickNextImageButton(WebDriver driver) {
        try {
            driver.findElement(By.xpath(".//button[contains(@class, '_6CZji')]")).click();
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("can not find next image button");
        }
    }

    private String getImageUrlFromMultipleImage(WebElement li) {
        try {
            return li.findElement(By.tagName("img")).getAttribute("src");
        } catch (Exception e) {
            log.error("can not find img tag");
            return null;
        }
    }

    private String getImageUrlFromSingleImage(WebDriver driver) {
        try {
            return driver.findElement(By.xpath(".//img[contains(@class, 'FFVAD')]")).getAttribute("src");
        } catch (Exception e) {
            log.error("can not find single img");
            return null;
        }
    }

    private List<WebElement> getImageElementList(WebDriver driver) {
        try {
            return driver.findElements(By.xpath(".//li[contains(@class, '_-1_m6')]"));
        } catch (Exception e) {
            return Lists.emptyList();
        }
    }

    private List<String> getImageUrlListFromDetailArticle(String url) {
        WebDriver driver = new FirefoxDriver();
        List<String> srcList = Lists.newArrayList();
        driver.get(url);
        List<WebElement> imageList = getImageElementList(driver);
        if (!imageList.isEmpty()) {
            imageList.forEach(li -> {
                String src = getImageUrlFromMultipleImage(li);
                if (!Strings.isNullOrEmpty(src)) srcList.add(src);
                clickNextImageButton(driver);
            });
        } else {
            srcList.add(getImageUrlFromSingleImage(driver));
        }
        driver.quit();
        return srcList;
    }

    private List<WebElement> getAnchorTagElement(WebDriver driver) {
        try {
            return driver.findElements(By.xpath(".//div[@class='v1Nh3 kIKUG  _bz0w']/a"));
        } catch (Exception e) {
            log.error("can not find article anchor list");
            return Lists.emptyList();
        }
    }

    private Set<String> getImageUrlSet(String userPath) {
        WebDriver rootDriver = new FirefoxDriver();
        try {
            rootDriver.get(INSTAGRAM_DOMAIN + userPath);
            int articleCount = getArticleCount(rootDriver);
            Set<String> processedPage = Sets.newHashSet();
            Set<String> imageLinkSet = Sets.newHashSet();

            while (articleCount > imageLinkSet.size()+160) {
                List<WebElement> aTagList = getAnchorTagElement(rootDriver);
                aTagList.stream()
                        .map(a -> a.getAttribute("href"))
                        .filter(url -> !processedPage.contains(url))
                        .forEach(url -> {
                            imageLinkSet.addAll(getImageUrlListFromDetailArticle(url));
                            processedPage.add(url);
                        });
                scrollToBottom(rootDriver);
            }
            rootDriver.quit();
            return imageLinkSet;
        } catch (Exception e) {
            rootDriver.quit();
            log.error(String.format("fail to get image urls. user path : %s", userPath));
            return Sets.newHashSet();
        }
    }

    private void fileDownloadFromImageUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            File directory = new File(String.valueOf(System.getProperty("user.dir") + "/download"));
            if (!directory.exists()) directory.mkdir();
            try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(directory.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg")) {
                FileCopyUtils.copy(in, out);
            } catch (Exception e) {
                log.error("fail to copy file");
            }
        } catch (MalformedURLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void scrapContents(String userPath) {
        Set<String> imageUrlSet = getImageUrlSet(userPath);
        imageUrlSet.forEach(imageLink -> {
            fileDownloadFromImageUrl(imageLink);
        });
    }
}
