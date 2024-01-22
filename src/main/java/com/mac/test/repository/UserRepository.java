package com.mac.test.repository;

import com.mac.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>  {
    User findByUsername(String username);
}
