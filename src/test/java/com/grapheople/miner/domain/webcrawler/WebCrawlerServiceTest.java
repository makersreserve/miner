package com.grapheople.miner.domain.webcrawler;

import org.assertj.core.util.Sets;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class WebCrawlerServiceTest {

    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
        WebDriver rootDriver = new FirefoxDriver();
        try {
            rootDriver.get("https://www.instagram.com/seongin_jee/"); // 로그인 페이지로 이동 합니다.


            int articleCount = Integer.parseInt(rootDriver.findElement(By.xpath(".//span[contains(@class, 'g47SY')][1]")).getText());

            Set<String> imageLinkSet = Sets.newHashSet();

            while (articleCount > imageLinkSet.size()) {
                JavascriptExecutor jse = (JavascriptExecutor) rootDriver;
                jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                List<WebElement> aTagList = rootDriver.findElements(By.xpath(".//div[@class='v1Nh3 kIKUG  _bz0w']/a"));
                aTagList.stream().forEach(anchorTag -> {
                    WebDriver driver = new FirefoxDriver();
                    try {
                        driver.get(anchorTag.getAttribute("href"));
                        List<WebElement> imageList = driver.findElements(By.xpath(".//li[contains(@class, '_-1_m6')]"));
                        if (!imageList.isEmpty()) {
                            imageList.forEach(li -> {
                                try {
                                    imageLinkSet.add(li.findElement(By.tagName("img")).getAttribute("src"));
                                    driver.findElement(By.xpath(".//button[contains(@class, '_6CZji')]")).click();
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            try {
                                imageLinkSet.add(driver.findElement(By.xpath(".//img[contains(@class, 'FFVAD')]")).getAttribute("src"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (StaleElementReferenceException e) {
                    } finally {
                        driver.quit();
                    }

                });
            }
            rootDriver.quit();
            imageLinkSet.stream()
                    .forEach(imageLink -> {
                        try {
                            System.out.println(imageLink);
                            URL url = new URL(imageLink);
                            InputStream in = url.openStream();
                            OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/download/" + System.currentTimeMillis() + ".jpg");
                            FileCopyUtils.copy(in, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            rootDriver.quit();
            e.printStackTrace();
        }
    }
}
