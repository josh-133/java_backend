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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for handling CRUD API requests for recipe book app
 */
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/")
public class RecipeController {
    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeRepository repository;
    private List<Recipe> recipes = new ArrayList<Recipe>();

    /**
     * Constructor for RecipeController
     * @param repository The RecipeRepository instance.
     */
    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    /**
     * Handles HTTP OPTIONS requests.
     * This method is used to handle pre-flight requests for cross-origin resource sharing (CORS)
     * allowing the frontend to check which HTTP methods are allowed on the server.
     * 
     * @return A ResponseEntity with HTTP status 200 OK.
     */
    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    /**
     * Get all recipes.
     * @return ResponseEntity containing a list of all recipes.
     */
    @GetMapping("recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = repository.findAll();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    /**
     * Get a specific recipy by ID.
     * @param id The ID of the recipe.
     * @return ResponseEntity containing the found recipe or NOT_FOUND if not found.
     */
    @GetMapping("recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
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

    /**
     * Creates a new recipe
     * @param newRecipe The Recipe object to be created.
     * @return ResponseEntity containing the created recipe
     */
    @PostMapping(path = "/recipes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe newRecipe) {
            Recipe savedRecipe = repository.save(newRecipe);
            return new ResponseEntity<>(savedRecipe, HttpStatus.OK);
        }

    /**
     * Updates an existing recipe
     * @param id The ID of the recipe
     * @param newRecipe The Recipe object to be created.
     * @return ResponseEntity containing the updated recipe
     */
    @PostMapping(path = "/recipes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe newRecipe) {
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

    /**
     * Delete a specific recipe
     * @param id The ID of the recipe
     * @return ResponseEntity containing the deleted recipe
     */
    @DeleteMapping("recipes/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable String id) {
        Optional<Recipe> fetchedRecipe = repository.findById(id);
        if (fetchedRecipe.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(fetchedRecipe.get(), HttpStatus.OK);
    }

    /**
     * Deletes all recipes
     * @return ResponseEntity containing all recipes deleted
     */
    @DeleteMapping("recipes")
    public ResponseEntity<List<Recipe>> deleteRecipes() {
        List<Recipe> fetchedRecipes = repository.findAll();
        repository.deleteAll();
        return new ResponseEntity<>(fetchedRecipes, HttpStatus.OK);
    }
}

