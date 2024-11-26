package com.aluracursos.challenge_literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Long anioDeNacimiento,
        @JsonAlias("death_year") Long anioDeFallecimiento
) {
}
