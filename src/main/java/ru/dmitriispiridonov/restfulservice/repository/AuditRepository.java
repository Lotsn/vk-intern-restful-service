package ru.dmitriispiridonov.restfulservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmitriispiridonov.restfulservice.dto.entity.AuditEntity;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {}
