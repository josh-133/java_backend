package com.dewc.gdp_recipe_book;

import org.springframework.data.mongodb.repository.MongoRepository;

interface RecipeRepository extends MongoRepository<Recipe, String> {

}
