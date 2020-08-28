package org.ant.wed.repositories;

import org.ant.wed.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String Email);

    Boolean existsByEmail(String Email);
}
