package br.com.bingo.bingo_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@EnableReactiveMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication()
public class BingoGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(BingoGameApplication.class, args);
	}

}
