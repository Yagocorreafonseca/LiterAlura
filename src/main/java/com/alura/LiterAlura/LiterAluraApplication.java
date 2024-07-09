package com.alura.LiterAlura;

import com.alura.LiterAlura.Repository.AutorRepository;
import com.alura.LiterAlura.Repository.LivroRepository;
import com.alura.LiterAlura.Principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner{
	@Autowired
	AutorRepository autorRepository;
	@Autowired
	LivroRepository livroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository,livroRepository);
		principal.principal();
	}
}

