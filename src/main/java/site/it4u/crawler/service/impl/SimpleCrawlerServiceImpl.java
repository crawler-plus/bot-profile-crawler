package site.it4u.crawler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import site.it4u.crawler.constant.Constants;
import site.it4u.crawler.domain.TransferEntity;
import site.it4u.crawler.service.SimpleCrawlerService;
import site.it4u.crawler.utils.JsoupUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimpleCrawlerServiceImpl implements SimpleCrawlerService {

    @Override
    public void crawlerPics(String picType, String fileName) {
        List<String> picList = new ArrayList<>(); // store images url links
        TransferEntity te = new TransferEntity();
        try {
            for(int i = 1; i <= Constants.EACH_TYPES_TOTAL_PAGE; i ++) {
                String formatUrl = String.format(Constants.CRAW_TEMPLATE_URL, picType, i);
                te.setUrl(formatUrl);
                te.setPattern("img[srcset]");
                Elements elements = JsoupUtils.getUrlContentsByPattern(te);
                if(elements.size() == 0) continue;
                List<String> subList = elements
                        .stream()
                        .map(t -> {
                            String srcSet = t.attr("srcset");
                            return srcSet.substring(0, srcSet.indexOf("?"));
                        })
                        .collect(Collectors.toList());
                picList.addAll(subList);
               if(picList.size() >= Constants.EACH_TYPES_RECORD_NUMBER) break;
            }
            List<String> collect = picList
                    .stream()
                    .map(t -> t.concat("?auto=compress&fit=crop&cs=tinysrgb&h=540&w=540"))
                    .limit(Constants.EACH_TYPES_RECORD_NUMBER) // get only 250 records
                    .collect(Collectors.toList());
            FileUtils.writeLines(new File(fileName), collect); // write to file
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}