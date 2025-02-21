package com.dewc.gdp_recipe_book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class RecipeController {
    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeRepository repository;
    private List<Recipe> recipes = new ArrayList<Recipe>();

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        try {
            Optional<Recipe> recipe = recipes.stream()
                .filter(_recipe -> id.equals(_recipe.getId()))
                .findAny();

            if (recipe.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Woopsie! Exception while looking for recipe");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

