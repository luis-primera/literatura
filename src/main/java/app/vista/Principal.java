package app.vista;

import app.dto.AutorDto;
import app.dto.LibroDto;
import app.modelo.Autor;
import app.modelo.Libro;
import app.repositorio.AutorRepositorio;
import app.repositorio.LibroRepositorio;
import app.servicio.BuscaLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private BuscaLibro buscaLibro = new BuscaLibro();

    private final LibroRepositorio libroRepositorio;
    private final AutorRepositorio autorRepositorio;

    @Autowired
    public Principal(LibroRepositorio libroRepo, AutorRepositorio autorRepo){
        this.libroRepositorio=libroRepo;
        this.autorRepositorio=autorRepo;
    }

    public void opcionesMenu() {
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Ingrese una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro que desea buscar: ");
                    String tituloLibro = teclado.nextLine();
                    getLibro(tituloLibro);
                    break;

                case 2:
//                    listarLibros();
                    break;

                case 3:
//                    listarAutores();
                    break;

                case 4:
//                    listarAutoresVivosEnAnio();
                    break;

                case 5:
//                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    teclado.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("La opción no es válida. Por favor intente de nuevo.");
            }

        } while (opcion != 0);

    }

    public LibroDto getLibro (String tituloLibro) {
        try {
            LibroDto libro = buscaLibro.obtenerLibro(tituloLibro);
            System.out.println("--------------------------------------------------------------------");
            System.out.println();
            System.out.println(libro.toString());
            AutorDto autor = libro.autorDto().get(0);
            System.out.println("--------------------------------------------------------------------");
            System.out.println();
            System.out.println(autor.toString());
            System.out.println("--------------------------------------------------------------------");
            guardarLibroEnBaseDeDatos(libro);
            return libro;
        } catch (Exception e){
            System.out.println(" Error al buscar el libro: " + e.getMessage());
            return null;
        }

    }

    public static void mostrarMenu() {
        System.out.println("\n----- MENÚ DE OPCIONES -----");
        System.out.println("1 - Buscar libro");
        System.out.println("2 - Listar libros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos en un año");
        System.out.println("5 - Listar libros por idioma");
        System.out.println("0 - Salir");
    }
    private void guardarLibroEnBaseDeDatos(LibroDto libroDto) {
        try {
            AutorDto autorDto = libroDto.autorDto().get(0);
            var autorExistente = autorRepositorio.findByNombre(autorDto.nombre());

            Autor autor;
            Libro libroGuardar = new Libro(libroDto);
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            }else {
                autor = new Autor(autorDto);
                autorRepositorio.save(autor);
            }
            libroGuardar.setAutor(autor); // Siempre se debe asociar el autor
            libroRepositorio.save(libroGuardar);

            System.out.println("✅ Libro y autor guardados en la base de datos.");
        }catch (Exception e){
            System.out.println("Error al guardar el libro. "+e.getMessage());
        }

    }

}
