package app.modelo;

import app.dto.AutorDto;
import app.dto.LibroDto;
import jakarta.persistence.*;


@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String lenguages;
    private int descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(){}

    public Libro(LibroDto libroDto){

        this.titulo = libroDto.titulo();
        this.lenguages = libroDto.lenguajes().get(0);
        this.descargas = libroDto.descargas();

        AutorDto autorDto = libroDto.autorDto().get(0);
        this.autor = new Autor(autorDto);
        this.autor.getLibros().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLenguages() {
        return lenguages;
    }

    public void setLenguages(String lenguages) {
        this.lenguages = lenguages;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return
                "📖 Titulo: " + titulo + "\n" +
                      "📖 Lenguajes: " + lenguages + "\n" +
                       "🌐 Descargas: " + descargas + "\n" +
                        "🧑 Autor: " + autor.getNombre();
    }

}

