package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import app.repositorio.AutorRepositorio;
import app.repositorio.LibroRepositorio;
import app.vista.Principal;

@SpringBootApplication

public class LiteraturaApplication  implements CommandLineRunner {
	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;


	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal=new Principal(libroRepositorio,autorRepositorio);
		principal.opcionesMenu();
	}
}
