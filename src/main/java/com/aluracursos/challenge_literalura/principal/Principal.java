package com.aluracursos.challenge_literalura.principal;

import com.aluracursos.challenge_literalura.models.Autor;
import com.aluracursos.challenge_literalura.models.Datos;
import com.aluracursos.challenge_literalura.models.DatosLibro;
import com.aluracursos.challenge_literalura.models.Libro;
import com.aluracursos.challenge_literalura.repository.LibroRepository;
import com.aluracursos.challenge_literalura.service.ConsumoAPI;
import com.aluracursos.challenge_literalura.service.ConvierteDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private String URL_BASE = "https://gutendex.com/books/";
    private final LibroRepository repository;
    private List<Libro> librosRegistrados;
    private List<Autor> autoresRegistrados;

    public Principal (LibroRepository repository) {
        this.repository = repository;
        this.librosRegistrados = new ArrayList<>();
        this.autoresRegistrados = new ArrayList<>();
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu = """
                    Elija la opción deseada:
                    
                    1- Buscar libro por título.
                    2- Listar libros registrados.
                    3- Listar Autores Registrados.
                    4- Listar autores vivos en un determinado año.
                    5- Listar libros por idioma.
                    0- Salir.""";
            try{
                System.out.println(menu);
                opcion = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Debe ingresar un número: " + e.getMessage());
                input.nextLine();
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPortitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
//                case 5:
//                    listarLibrosPorIdioma();
//                    break;
                case 0:
                    System.out.println("Finalizando la aplicación...");
                default:
                    System.out.println("Opcion inválida");
            }
        }
    }

    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese un año: ");
        long anio = input.nextLong();
        List<Autor> autoresVivos = repository.findDistinctAutoresByAutoresAnioDeFallecimientoLess(anio);
        if (!autoresVivos.isEmpty()) autoresVivos.forEach(a -> System.out.println(a.getNombre()));
    }

    private void listarAutoresRegistrados() {
        autoresRegistrados = repository.findDistinctAutores();
        if(!autoresRegistrados.isEmpty()){
            System.out.println("Autores registrados: ");
            autoresRegistrados.forEach(a -> System.out.println(a.getNombre()));
        } else {
            System.out.println("No hay autores registrados");
        }
    }

    private void listarLibrosRegistrados() {
        librosRegistrados = repository.findAll();
        if (!librosRegistrados.isEmpty()) {
            System.out.println("Los libros registrados son:");
            librosRegistrados.forEach(l -> System.out.println("Titulo: " + l.getTitulo()));
        } else {
            System.out.println("No hay libros registrados");
        }
        System.out.println();
    }

    private void buscarLibroPortitulo() {
        DatosLibro datosLibro = getDatosLibro();
        System.out.println(datosLibro);
        if (datosLibro != null) {
            Libro libro = new Libro(datosLibro);
            repository.save(libro);
        } else {
            System.out.println("No se encontró el libro");
        }

    }

    private DatosLibro getDatosLibro() {
        DatosLibro datosLibro = null;
        System.out.println("Escriba el nombre del libro que deseas buscar");
        String nombreLibro = input.nextLine();
        String url = URL_BASE + "?search=" + URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        var json = consumoAPI.obtenerDatos(url);
        System.out.println(json);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        if (!datos.libros().isEmpty()){
            datosLibro = datos.libros().get(0);
        }
        return datosLibro;
    }
}
