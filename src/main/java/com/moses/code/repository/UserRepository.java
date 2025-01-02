package com.moses.code.repository;

import com.moses.code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    @Query(value = "SELECT * FROM users u WHERE u.name LIKE %:name%", nativeQuery = true)
    List<User> findUsersByNameContains(@Param("name") String name);

    boolean existsByEmail(String email);

   Optional<User> findByEmail(String username);
}
