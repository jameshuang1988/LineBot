package james.LineBot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document  //標記這個class對映一個collection，在(user)這個collection裡面操作document
public class User {

    public User(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
    }

    @Id
    private String userId;

    private String name;
    private String rollNumber;
}
