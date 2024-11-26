package com.aluracursos.challenge_literalura.repository;

import com.aluracursos.challenge_literalura.models.Autor;
import com.aluracursos.challenge_literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT DISTINCT a FROM Autor a JOIN a.libros l")
    List<Autor> findDistinctAutores();

    @Query("SELECT DISTINCT a FROM Autor a JOIN a.libros l WHERE a.anioDeFallecimiento > :anioDeFallecimiento")
    List<Autor> findDistinctAutoresByAutoresAnioDeFallecimientoLess(Long anioDeFallecimiento);
}
