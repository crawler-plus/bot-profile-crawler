package site.it4u.crawler.domain;

import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
public class TransferEntity {

    private String url; // target url

    private String pattern; // regexp

    private String proxyIp; // proxy ip

    private int proxyPort; // proxy port

    private String httpMethod; // http method（GET/POST）

    /**
     * default constructor
     */
    public TransferEntity() {
        this.httpMethod = HttpMethod.GET.toString();
    }
}
