package james.LineBot.repository;

import james.LineBot.model.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, String> {

    Lesson findByIndex(Integer index);

    int countByStatus(Boolean status);

}
