package ru.dmitriispiridonov.restfulservice.dto.api.user;

public record User(
        Integer id,
        String name,
        String username,
        String email,
        Address address,
        String phone,
        String website,
        Company company) {}
