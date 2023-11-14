package com.example.blackmarket.repository;

import com.example.blackmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    User findBynameAndPassword(String username, String password);

    void deleteById(Long id);

    Long findIdByName(String name);

    User findByName(String name);

    Optional<User> findById(Long id);
}
