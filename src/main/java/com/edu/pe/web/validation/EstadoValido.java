package com.edu.pe.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EstadoValidoValidator.class)
public @interface EstadoValido {
    String message() default "Estado no válido. Los estados válidos son: 'Por hacer', 'En progreso', 'Completada'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}