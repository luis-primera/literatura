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
        String autor = "No disponible";

        if (autorDto != null && !autorDto.isEmpty()) {
            autor = autorDto.get(0).nombre();
        }
        return  "---- LIBRO ----"+"\n" +
                "📖 Titulo: " + titulo + "\n" +
                "🧑 Autor: " + autor+"\n" +
                "📖 Lenguajes: " + lenguajes.get(0) + "\n" +
                "🌐 Descargas: " + descargas;
    }
}
