package site.it4u.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.it4u.crawler.constant.Constants;
import site.it4u.crawler.domain.BaseEntity;
import site.it4u.crawler.service.SimpleCrawlerService;

import java.util.concurrent.CountDownLatch;

@Controller
@RequestMapping("/image")
public class CrawlerController {

    @Autowired
    private SimpleCrawlerService simpleCrawlerService;

    @GetMapping("/getAllTypes")
    @ResponseBody
    public BaseEntity getAllTypes() {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        new Thread(() -> {
            simpleCrawlerService.crawlerPics(Constants.MEN_TYPE, Constants.MEN_TYPE_FILE);
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            simpleCrawlerService.crawlerPics(Constants.WOMEN_TYPE, Constants.WOMEN_TYPE_FILE);
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            simpleCrawlerService.crawlerPics(Constants.ANIMAL_TYPE, Constants.ANIMAL_TYPE_FILE);
            countDownLatch.countDown();
        }).start();
        new Thread(() -> {
            simpleCrawlerService.crawlerPics(Constants.NATURE_TYPE, Constants.NATURE_TYPE_FILE);
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            return new BaseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        return new BaseEntity();
    }

    @GetMapping("/getMen")
    @ResponseBody
    public BaseEntity getMen() {
        simpleCrawlerService.crawlerPics(Constants.MEN_TYPE, Constants.MEN_TYPE_FILE);
        return new BaseEntity();
    }

    @GetMapping("/getWomen")
    @ResponseBody
    public BaseEntity getWomen() {
        simpleCrawlerService.crawlerPics(Constants.WOMEN_TYPE, Constants.WOMEN_TYPE_FILE);
        return new BaseEntity();
    }

    @GetMapping("/getAnimal")
    @ResponseBody
    public BaseEntity getAnimal() {
        simpleCrawlerService.crawlerPics(Constants.ANIMAL_TYPE, Constants.ANIMAL_TYPE_FILE);
        return new BaseEntity();
    }

    @GetMapping("/getNature")
    @ResponseBody
    public BaseEntity getNature() {
        simpleCrawlerService.crawlerPics(Constants.NATURE_TYPE, Constants.NATURE_TYPE_FILE);
        return new BaseEntity();
    }
}
