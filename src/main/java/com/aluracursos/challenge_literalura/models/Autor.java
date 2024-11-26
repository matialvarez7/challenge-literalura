package com.aluracursos.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Long anioDeNacimiento;
    private Long anioDeFallecimiento;
    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

    public Autor(){};

    public Autor (DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioDeNacimiento = datosAutor.anioDeNacimiento();
        this.anioDeFallecimiento = datosAutor.anioDeFallecimiento();
        this.libros = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Long anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Long getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Long anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void agregarLibro(Libro libro){
        this.libros.add(libro);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anioDeNacimiento=" + anioDeNacimiento +
                ", anioDeFallecimiento=" + anioDeFallecimiento +
                ", libros=" + libros +
                '}';
    }
}
