package ru.dmitriispiridonov.restfulservice.dto.api.user;

public record Todo(Integer userId, Integer id, String title, Boolean completed) {}
