package com.edu.pe;

import com.edu.pe.application.service.TareaService;
import com.edu.pe.domain.Tarea;
import com.edu.pe.infrastructure.repository.ITareaRepository;
import com.edu.pe.web.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class TareaServiceTest {

    @Mock
    private ITareaRepository tareaRepository;

    @InjectMocks
    private TareaService tareaService;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tarea = new Tarea();
        tarea.setId(1);
        tarea.setTitulo("Titulo 1");
        tarea.setDescripcion("Descripción de la tarea 1");
    }

    @Test
    void testCrearTarea() {
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        ApiResponse<Tarea> response = tareaService.crear(tarea);
        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(tarea, response.getData());
    }

    @Test
    void testListarTareas() {
        when(tareaRepository.findAll()).thenReturn(List.of(tarea));

        List<Tarea> tareas = tareaService.listar();

        assertNotNull(tareas);
        assertEquals(1, tareas.size());
        assertEquals(tarea, tareas.get(0));
    }

    @Test
    void testObtenerTareaPorId() {
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));

        ApiResponse<Tarea> response = tareaService.obtenerPorId(1);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(tarea, response.getData());
    }

    @Test
    void testActualizarTarea() {
        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1);
        tareaActualizada.setTitulo("Tarea Actualizada");
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaActualizada);

        ApiResponse<Tarea> response = tareaService.actualizar(1, tareaActualizada);

        assertNotNull(response);
        assertTrue(response.isStatus());
        assertEquals(tareaActualizada, response.getData());
    }

    @Test
    void testActualizarTareaNotFound() {
        Tarea tareaActualizada = new Tarea();
        tareaActualizada.setId(1);
        tareaActualizada.setTitulo("Tarea Actualizada");
        when(tareaRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            tareaService.actualizar(1, tareaActualizada);
        });
        assertEquals("Tarea no encontrada con ID: 1", thrown.getReason());
    }

    @Test
    void testEliminarTarea() {
        when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
        doNothing().when(tareaRepository).deleteById(1);

        ApiResponse<String> response = tareaService.eliminar(1);

        assertNotNull(response);
        assertTrue(response.isStatus());
    }
}
