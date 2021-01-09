package com.kingcourier.kingcourier.repository;

import com.kingcourier.kingcourier.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
