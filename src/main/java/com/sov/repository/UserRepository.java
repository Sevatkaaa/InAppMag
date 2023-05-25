package com.sov.repository;

import com.sov.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> getUserByUsername(String username);
    Optional<UserModel> getUserById(Long id);
}
