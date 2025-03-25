package com.dewc.gdp_recipe_book;

/**
 * Repository interface for accessing and managing {@link Recipe} entities in the MongoDB database.
 * Extends {@link MongoRepository} to provide CRUD operations.
 */

import org.springframework.data.mongodb.repository.MongoRepository;

interface RecipeRepository extends MongoRepository<Recipe, String> {

}
