package com.PTIV.loja.de.games;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.stripe.Stripe;

@SpringBootApplication
@EntityScan(basePackages = "com.PTIV.loja.de.games.model")
public class LojaDeGamesApplication {
	@PostConstruct
	public void setup(){
		Stripe.apiKey="sk_test_51JHr20IkXPmyXB6SMX8ekXNvL2Zf4QOJGWP16Kjbwsx" +
				"7XVIAnbpH3rhr1EdCZkLmuVTs1tsU30KgdNDG4IhZQ0fz0053QCUEwC";
	}
	public static void main(String[] args) {
		SpringApplication.run(LojaDeGamesApplication.class, args);
	}

}
