package ru.dmitriispiridonov.restfulservice.dto.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String name;
    private String password;
}
