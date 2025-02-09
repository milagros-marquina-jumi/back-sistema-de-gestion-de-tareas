package com.edu.pe.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.edu.pe.domain.Tarea;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Integer> {
}