package app.repositorio;


import app.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anno_nacimiento <= :anio AND (a.anno_fallecimiento IS NULL OR a.anno_fallecimiento >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);
}


