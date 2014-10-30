package core.september.rescue.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import core.september.rescue.model.Profile;


@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
public interface ProfileRepo extends MongoRepository<Profile, String> {

}
