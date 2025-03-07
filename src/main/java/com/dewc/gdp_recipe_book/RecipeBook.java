package com.dewc.gdp_recipe_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages= "com.dewc.gdp_recipe_book")
public class RecipeBook {
	public static void main(String[] args) {
		SpringApplication.run(RecipeBook.class, args);
	}
}
