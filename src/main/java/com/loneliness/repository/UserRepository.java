package com.loneliness.repository;

import com.loneliness.entity.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @NotNull
    @Override
    Optional<User> findById(Integer integer);
    @NotNull
    Optional<User> findByUsername(String username);
    @NotNull
    Optional<User> findUserByGoogleId(String id);
}
