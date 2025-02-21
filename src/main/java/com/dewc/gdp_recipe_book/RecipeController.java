package com.dewc.gdp_recipe_book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        List<Recipe> recipes = repository.findAll();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        try {
            Optional<Recipe> recipe = repository.findById(id);
            if (recipe.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Woopsie! Exception while looking for recipe");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE
    @PostMapping(path = "/recipes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe newRecipe) {
            Recipe savedRecipe = repository.save(newRecipe);
            return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
        }

    // UPDATE
    @PostMapping(path = "/recipes{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe newRecipe) {
            Optional<Recipe> fetchedRecipe = repository.findById(id);
            // If Recipe does not exist in the DB
            if (fetchedRecipe.get().equals(newRecipe)) {
                return new ResponseEntity<>(newRecipe, HttpStatus.OK);
            }
            // Else Update the Recipe in the DB
            Recipe recipeToUpdate = fetchedRecipe.get();
            recipeToUpdate.setTitle(newRecipe.getTitle());
            recipeToUpdate.setIngredients(newRecipe.getIngredients());
            recipeToUpdate.setMethod(newRecipe.getMethod());
            Recipe savedRecipe = repository.save(recipeToUpdate);
            return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
        }

    // DELETE
    @DeleteMapping("recipes/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> fetchedRecipe = repository.findById(id);
        if (fetchedRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(fetchedRecipe.get(), HttpStatus.OK);
    }

    @DeleteMapping("recipes")
    public ResponseEntity<List<Recipe>> deleteRecipes() {
        List<Recipe> fetchedRecipes = repository.findAll();
        repository.deleteAll();
        return new ResponseEntity<>(fetchedRecipes, HttpStatus.OK);
    }
}

