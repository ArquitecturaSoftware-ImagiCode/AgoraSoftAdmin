package com.imagicode.agorasoftadmin.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.imagicode.agorasoftadmin.entidades.PlazaInstance;

public interface PlazaInstanceRepository extends JpaRepository<PlazaInstance, Long> {
    List<PlazaInstance> findByPlazaIdAndActiveTrue(Long plazaId);
}