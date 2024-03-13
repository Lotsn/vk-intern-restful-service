package ru.dmitriispiridonov.restfulservice.dto.api.post;

public record Comment(Integer postId, Integer id, String name, String email, String body) {}
