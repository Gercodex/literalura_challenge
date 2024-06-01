package com.cursoalura.literalura_challenge;

import com.cursoalura.literalura_challenge.principal.Principal;
import com.cursoalura.literalura_challenge.repositorio.RepositorioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraChallengeApplication implements CommandLineRunner {

	@Autowired
	private RepositorioAutor repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioAutor);
		principal.mostrarMenu();
	}
}
