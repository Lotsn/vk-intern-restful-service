package ru.dmitriispiridonov.restfulservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitriispiridonov.restfulservice.dto.entity.UserEntity;
import ru.dmitriispiridonov.restfulservice.repository.UserRepository;

@Service
@AllArgsConstructor
public class AddUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
