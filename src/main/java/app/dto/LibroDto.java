package app.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDto(
        @JsonAlias("title") String titulo,
        @JsonAlias ("authors") List<AutorDto> autorDto,
        @JsonAlias ("languages") List<String> lenguajes,
        @JsonAlias ("download_count") int descargas
) {
    @Override
    public String toString() {
        return "Libro{" + '\'' +
                "Titulo =" + titulo + '\'' +
                ", Autor ='" + autorDto.get(0).nombre() + '\'' +
                ", Lenguaje =" + lenguajes.get(0) + '\'' +
                ", Descargas =" + descargas +
                '}';
    }
}
