package site.it4u.crawler.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import site.it4u.crawler.constant.Constants;
import site.it4u.crawler.domain.TransferEntity;

import java.util.HashMap;
import java.util.Map;

public class JsoupUtils {

    /**
     * @return jsoup document object
     */
    public static Document getUrlDocument(TransferEntity te) throws Exception {
        return getUrlDocument(te, null, null);
    }

    /**
     * @return jsoup document object
     */
    public static Document getUrlDocument(TransferEntity te, Map<String, String> reqHeadersMap, Map<String, String> postParamsMap) throws Exception {
        Connection conn = Jsoup.connect(te.getUrl());
        Document docData;
        // request header setting
        if(!CollectionUtils.isEmpty(reqHeadersMap)) {
            conn.headers(defaultHeadersMap());
        }
        // proxy setting
        if(!StringUtils.isEmpty(te.getProxyIp())) {
            conn.proxy(te.getProxyIp(), te.getProxyPort());
        }
        // request body setting
        if(!CollectionUtils.isEmpty(postParamsMap)) {
            conn.data(postParamsMap);
        }
        if(HttpMethod.POST.toString().toUpperCase().equals(te.getHttpMethod().toUpperCase())) {
            docData = conn.timeout(Constants.JSOUP_CONNECT_DEFAULT_TIMEOUT).post();
        }else {
            docData = conn.timeout(Constants.JSOUP_CONNECT_DEFAULT_TIMEOUT).get();
        }
        return docData;
    }

    /**
     * @return Elements
     */
    public static Elements getUrlContentsByPattern(TransferEntity te) throws Exception {
        return getUrlContentsByPattern(te, null, null);
    }

    /**
     * @return Elements
     */
    public static Elements getUrlContentsByPattern(TransferEntity te, Map<String, String> reqHeadersMap, Map<String, String> postParamsMap) throws Exception {
        Document docData = getUrlDocument(te, reqHeadersMap, postParamsMap);
        Elements elements = docData.select(te.getPattern());
        return elements;
    }

    /**
     * default header settings
     * @return
     */
    private static Map<String, String> defaultHeadersMap() {
        Map<String, String> reqHeadersMap = new HashMap<>();
        reqHeadersMap.put("Accept", "*/*");
        reqHeadersMap.put("X-Requested-With", "XMLHttpRequest");
        reqHeadersMap.put("User-Agent", "Mozilla/5.0 (Windows N" +
                "T 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        reqHeadersMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        reqHeadersMap.put("Accept-Encoding", "gzip, deflate");
        reqHeadersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        return reqHeadersMap;
    }
}
