package app.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public record AutorDto(
        @JsonAlias ("name") String nombre,
        @JsonAlias ("birth_year") Integer fecha_nacimiento,
        @JsonAlias ("death_year") Integer fecha_fallecimiento
) {
    @Override
    public String toString() {
        return "Autor{" + '\'' +
                "Nombre=" + nombre + '\'' +
                ", Fecha de Nacimiento ='" + fecha_nacimiento + '\'' +
                ", Fecha de Fallecimiento=" + fecha_fallecimiento +
                '}';
    }
}
