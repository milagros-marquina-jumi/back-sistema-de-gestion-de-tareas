package com.edu.pe.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.pe.domain.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> { 
   public Usuario findByCorreo(String correo); 
}