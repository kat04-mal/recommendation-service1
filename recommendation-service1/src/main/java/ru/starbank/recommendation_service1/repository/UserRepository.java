package ru.starbank.recommendation_service1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.starbank.recommendation_service1.entity.UserEntity;

import java.util.List;
import java.util.UUID;


@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, UUID> { List<UserEntity> findByUsername(String username);
}