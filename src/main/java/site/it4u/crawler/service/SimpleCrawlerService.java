package site.it4u.crawler.service;

public interface SimpleCrawlerService {

    /**
     * @param picType men/women/animal/nature
     * @param fileName file name to be stored
     */
    void crawlerPics(String picType, String fileName);
}
