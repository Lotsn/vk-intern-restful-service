package ru.dmitriispiridonov.restfulservice.dto.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "audit")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date requestTime;
    private String name;
    private String roles;
    private String request;
    private String params;
    private String method;
    private Integer httpStatus;
}
