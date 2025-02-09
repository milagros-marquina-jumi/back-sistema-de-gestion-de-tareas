package com.edu.pe.web.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstadoValidoValidator implements ConstraintValidator<EstadoValido, String> {

    private static final String[] ESTADOS_VALIDOS = {"Por hacer", "En progreso", "Completada"};

    @Override
    public boolean isValid(String estado, ConstraintValidatorContext context) {
        if (estado == null) {
            return false;  
        }
        for (String valido : ESTADOS_VALIDOS) {
            if (valido.equalsIgnoreCase(estado)) {
                return true;  
            }
        }
        return false;  // Si no coincide, no es válido
    }
}