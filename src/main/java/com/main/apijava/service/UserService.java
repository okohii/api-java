package com.main.apijava.service;

import com.main.apijava.controller.CreateUserDto;
import com.main.apijava.controller.UpdateUserDto;
import com.main.apijava.entity.User;
import com.main.apijava.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        var entity = new User(UUID.randomUUID(),
                createUserDto.nome(),
                createUserDto.idade(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getId();
    }

    public User getUserById(UUID id) {
        var user = userRepository.findById(id).orElse(null);

        return user;
    }

    public List<User> getAllUsers() {
        var users = userRepository.findAll();
        return users;
    }

    public void deleteById(UUID id) {
            userRepository.deleteById(id);
    }

    public void updateById(UUID id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (updateUserDto.nome() != null) {
                user.setNome(updateUserDto.nome());
            }

            if (updateUserDto.idade() != null) {
                user.setIdade(updateUserDto.idade());
            }
            userRepository.save(user);
        }
    }
}