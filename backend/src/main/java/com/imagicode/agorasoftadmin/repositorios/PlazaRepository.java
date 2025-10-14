package com.imagicode.agorasoftadmin.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoftadmin.entidades.Plaza;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    Optional<Plaza> findByRepresentanteEmail(String email);
}
