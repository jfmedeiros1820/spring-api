package com.authentication.api.repository;

import com.authentication.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = "{userName:'?0'}")
    Optional<User> findUserByUsername(String username);
}
