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
import java.util.List;
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
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    System.out.print("Ingrese el año que desea consultar: ");
                    Integer anno = teclado.nextInt();
                    listarAutoresVivosPorAnio(anno);
                    break;

                case 5:
                    System.out.print("Ingrese el idioma que desea consultar (por ejemplo: 'en' o 'es'): ");
                    String idioma = teclado.nextLine();
                    listarLibrosPorIdioma(idioma);
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
            System.out.println("Libro encontrado:");
            System.out.println();
            System.out.println(libro.toString());
            System.out.println("--------------------------------------------------------------------");
            System.out.println();
            if(libro!=null){
                guardarLibroEnBaseDeDatos(libro);
            }
            return libro;
        } catch (Exception e){
            e.printStackTrace();
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
            Autor autor = null;

            if (libroDto.autorDto() != null && !libroDto.autorDto().isEmpty()) {
                AutorDto autorDto = libroDto.autorDto().get(0);
                var autorExistente = autorRepositorio.findByNombre(autorDto.nombre());

                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(autorDto);
                    autorRepositorio.save(autor);
                    System.out.println("✅ Libro y autor guardados en la base de datos.");
                }
            } else {
                System.out.println("⚠️ Libro sin autor. Se guardará con autor null.");
            }

            Libro libroGuardar = new Libro(libroDto);
            libroGuardar.setAutor(autor);
            libroRepositorio.save(libroGuardar);

            System.out.println("✅ Libro guardado en la base de datos.");
        } catch (Exception e) {
            System.out.println("❌ Error al guardar el libro: " + e.getMessage());
        }
    }
    private void listarLibrosRegistrados() {
        var libros = libroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("📭 No hay libros registrados.");
        } else {
            System.out.println("📚 Libros registrados en la base de datos:");
            System.out.println("---------------");
            libros.forEach(libro -> {
                System.out.println(libro.toString());
                System.out.println("---------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("📭 No hay autores registrados.");
        } else {
            System.out.println("👨‍🏫 Autores registrados en la base de datos:");
            System.out.println("---------------");
            autores.forEach(autor -> {
                System.out.println(autor.toString());
                System.out.println("---------------");

            });
        }
    }

    public void listarAutoresVivosPorAnio(Integer anio) {
        List<Autor> autoresVivos = autorRepositorio.findAutoresVivosEnAnio(anio);

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println();
        if (autoresVivos.size()>0){
            System.out.println("📅 Autores vivos en el año " + anio + ":");
            for (Autor autor : autoresVivos) {
                System.out.println("👤 " + autor.getNombre());
            }
        }else {
            System.out.println();
            System.out.println("No hay autores vivos registrados en este año. 🙁");
        }

        System.out.println("-----------------------------");
    }

    public void listarLibrosPorIdioma(String idioma) {

        try {
            List<Libro> librosPorIdioma = libroRepositorio.findByLenguagesIgnoreCase(idioma);
            System.out.println("-----------------------------");
            if (librosPorIdioma.isEmpty()) {
                System.out.println("📭 No se encontraron libros en el idioma: " + idioma);
            } else {
                System.out.println("📚 Libros en idioma '" + idioma + "':");
                librosPorIdioma.forEach(libro -> System.out.println("📖 " + libro.getTitulo()));
            }
            System.out.println("-----------------------------");
        } catch (Exception e){
            System.out.println("Error al consultar los libros registrados por idioma.");
        }

    }
}
