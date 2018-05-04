package site.it4u.crawler.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BaseEntity {

    private int msgCode;

    private Object content;

    public BaseEntity() {
        this.msgCode = HttpStatus.OK.value();
        this.content = HttpStatus.OK.getReasonPhrase();
    }
}
