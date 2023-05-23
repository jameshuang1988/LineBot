package james.LineBot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class JavaLineBot {

    @Id
    private String _id;

    private Integer index;

    private String content;

    private Boolean status;

    private Integer localeType;

    public JavaLineBot(Integer index, String content, Boolean status, Integer localeType) {
        this.index = index;
        this.content = content;
        this.status = true;
        this.localeType = 0;
    }
}