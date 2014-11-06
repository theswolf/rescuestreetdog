package core.september.rescue.repo;

//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import core.september.rescue.model.User;

public interface UserRepo extends MongoRepository<User, String> {
}
