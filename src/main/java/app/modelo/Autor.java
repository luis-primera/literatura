package app.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.dto.AutorDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anno_nacimiento;
    private Integer anno_fallecimiento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros= new ArrayList<>();

    public Autor(){}
    public Autor(AutorDto autorDto){
        this.nombre = autorDto.nombre();
        this.anno_nacimiento = autorDto.fecha_nacimiento();
        this.anno_fallecimiento = autorDto.fecha_fallecimiento();
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

    public Integer getAnno_nacimiento() {
        return anno_nacimiento;
    }

    public void setAnno_nacimiento(Integer anno_nacimiento) {
        this.anno_nacimiento = anno_nacimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Integer getAnno_fallecimiento() {
        return anno_fallecimiento;
    }

    public void setAnno_fallecimiento(Integer anno_fallecimiento) {
        this.anno_fallecimiento = anno_fallecimiento;
    }

    @Override
    public String toString() {
        return
                "🧑 Autor: " + nombre + "\n" +
                "🎂 Fecha de Nacimiento: " + anno_nacimiento + "\n" +
                "⚰️ Fecha de Fallecimiento: " + anno_fallecimiento + "\n" +
                "📚 Libros: " + obtenerTitulosLibros();
    }
    private String obtenerTitulosLibros() {
        List<String> titulos = new ArrayList<>();
        for (Libro libro : libros) {
            titulos.add(libro.getTitulo());
        }
        return titulos.toString();
    }
}

