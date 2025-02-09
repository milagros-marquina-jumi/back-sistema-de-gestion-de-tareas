package com.edu.pe.domain;

import com.edu.pe.web.validation.EstadoValido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private int id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder los 200 caracteres")
    @Column(length = 200, nullable = false)
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no puede exceder los 20 caracteres")
    @Column(length = 20, nullable = false)
    @EstadoValido 
    private String estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

	@Override
	public String toString() {
		return "Tarea [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", estado=" + estado + "]";
	}
}
