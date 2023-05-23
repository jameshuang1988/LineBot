package james.LineBot.repository;

import james.LineBot.model.JavaLineBot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JavaLineBotRepository extends MongoRepository<JavaLineBot, String> {

    JavaLineBot findByIndex(Integer index);

    int countByStatus(Boolean status);

}
