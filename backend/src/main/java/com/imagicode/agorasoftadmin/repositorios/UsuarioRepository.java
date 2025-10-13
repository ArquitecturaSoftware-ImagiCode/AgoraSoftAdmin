    package com.imagicode.agorasoftadmin.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagicode.agorasoftadmin.entidades.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
