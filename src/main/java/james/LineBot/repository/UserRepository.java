package james.LineBot.repository;

import james.LineBot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

// * Please keep in mind if you want to create one or more document (Table) for that every document (Table) you need to define a new interface that will extend MongoRepository.
// * MongoRepository<User,String> here “User” is my class name and “String” is my ID data type that we already defined in the User class and annotated with “@Id”.
public interface UserRepository extends MongoRepository<User, String> {

}
