package app.repositorio;

import app.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByLenguagesIgnoreCase(String lenguages);
}
