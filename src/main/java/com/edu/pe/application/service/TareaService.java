package com.edu.pe.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.edu.pe.domain.Tarea;
import com.edu.pe.infrastructure.repository.ITareaRepository;
import com.edu.pe.web.response.ApiResponse;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

	@Autowired
    private ITareaRepository tareaRepository;

	@Transactional
	public ApiResponse<Tarea> crear(Tarea tarea) {
	    try {
	        Tarea tareaGuardada = tareaRepository.save(tarea);
	        
	        if (tareaGuardada != null) {
	            return new ApiResponse<>("Tarea creada exitosamente", tareaGuardada); 
	        }
	        return new ApiResponse<>("La tarea no se pudo crear", null, false);  
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ApiResponse<>("Error al crear tarea", null, false);  
	    }
	}

    public List<Tarea> listar() {
        try {
            return tareaRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener las tareas", e);
        }
    }

    public ApiResponse<Tarea> obtenerPorId(Integer id) {
    	Tarea obj = tareaRepository.findById(id).orElse(null);
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada con ID: " + id);
        }
        return new ApiResponse<>("Tarea encontrada exitosamente", obj);
    }
    
    @Transactional
    public ApiResponse<Tarea> actualizar(Integer id, Tarea tareaActualizada) {
        Optional<Tarea> tareaOptional = tareaRepository.findById(id);
        if (!tareaOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada con ID: " + id);
        }
        try {
            tareaActualizada.setId(id);
            Tarea nuevaTareaActualizada = tareaRepository.save(tareaActualizada);
            
            if(nuevaTareaActualizada != null) {
            	return new ApiResponse<>("Tarea actualizada exitosamente",nuevaTareaActualizada);
            }
            return new ApiResponse<>("La tarea no se pudo actualizar",nuevaTareaActualizada, false);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar tarea", e);
        }
    }
    
    @Transactional
    public ApiResponse<String> eliminar(Integer id) {
    	Optional<Tarea> tareaOptional = tareaRepository.findById(id);
        if (!tareaOptional.isPresent()) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada con ID: " + id);
        }
        try {
            tareaRepository.deleteById(id);
            
            if (tareaRepository.findById(id).isPresent()) {
                return new ApiResponse<>("No se pudo eliminar tarea", null, false); 
            }
            
            return new ApiResponse<>("Tarea eliminada exitosamente", "ID de tarea eliminada: " + id);
      
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar tarea", e);
        }
    }
}