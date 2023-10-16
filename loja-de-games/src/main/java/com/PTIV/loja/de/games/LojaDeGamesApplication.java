package com.PTIV.loja.de.games;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.PTIV.loja.de.games.model")
public class LojaDeGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaDeGamesApplication.class, args);
	}

}
