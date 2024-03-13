package ru.dmitriispiridonov.restfulservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmitriispiridonov.restfulservice.dto.entity.UserEntity;
import ru.dmitriispiridonov.restfulservice.service.AddUserService;

@RestController
@RequestMapping("/api/create-user")
@AllArgsConstructor
public class AddUserController {

    private final AddUserService addUserService;

    @PostMapping
    public String addUser(@AuthenticationPrincipal @RequestBody UserEntity user) {
        addUserService.addUser(user);
        return "User has saved";
    }
}
