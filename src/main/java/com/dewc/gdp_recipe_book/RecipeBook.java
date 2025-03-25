package com.dewc.gdp_recipe_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main entry point for the Recipe Book application.
 * This class initialises and runs the Spring Boot application.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages= "com.dewc.gdp_recipe_book")
public class RecipeBook {

	/**
	 * The main method that starts the Spring Boot application.
	 * 
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(RecipeBook.class, args);
	}
}
