package org.ant.wed.repositories;

import org.ant.wed.models.ERole;
import org.ant.wed.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
