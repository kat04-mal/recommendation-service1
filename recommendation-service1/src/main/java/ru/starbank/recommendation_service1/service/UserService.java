package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import ru.starbank.recommendation_service1.entity.UserEntity;
import ru.starbank.recommendation_service1.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;


    public UserService(
            UserRepository repository
    ) {
        this.repository = repository;
    }

    public UserEntity findByUsername(String username) {

        List<UserEntity> users =
                repository.findByUsername(username);


        if (users.size() != 1) {
            return null;
        }

        return users.get(0);
    }
}