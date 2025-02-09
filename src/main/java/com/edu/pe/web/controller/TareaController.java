package com.edu.pe.web.controller;

import com.edu.pe.application.service.TareaService;
import com.edu.pe.domain.Tarea;
import com.edu.pe.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody @Validated Tarea obj) {
    	ApiResponse<Tarea> response = tareaService.crear(obj);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> listarTareas() {
        List<Tarea> tareas = tareaService.listar();
        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTareaPorId(@PathVariable Integer id) {
    	ApiResponse<Tarea> response = tareaService.obtenerPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Integer id, @Validated @RequestBody Tarea tarea) {
        ApiResponse<Tarea> response = tareaService.actualizar(id, tarea);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Integer id) {
        ApiResponse<String> response = tareaService.eliminar(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}