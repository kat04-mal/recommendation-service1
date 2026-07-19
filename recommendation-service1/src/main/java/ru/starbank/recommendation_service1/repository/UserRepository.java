package ru.starbank.recommendation_service1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.starbank.recommendation_service1.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByUsername(String username);
}