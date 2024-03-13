package ru.dmitriispiridonov.restfulservice.dto.api.album;

public record Photo(Integer albumId, Integer id, String title, String url, String thumbnailUrl) {}
