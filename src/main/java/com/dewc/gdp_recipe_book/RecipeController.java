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
    private List<Recipe> recipes = new ArrayList<Recipe>();

    public RecipeController() {
        recipes.add(new Recipe(
            "BLT Sandwich",
            new String[] { "Bacon", "Lettuce", "Tomato" },
            "1. Toast Bread\n 2. Place bacon, lettuce and tomato on a slice of Toast \n3. Put last slice of toast on top."
        ));

        recipes.add(new Recipe(
                "Baked Beans",
                new String[] { "Can of Heinz Baked Beans" },
                "1. Open Microwave\n2. Empty can of baked beans into a bowl\n3. Heat baked beans for 2 minutes"
        ));
        recipes.add(new Recipe(
                "2 Minute Noodles",
                new String[] { "Cup of Noodles", "Egg" },
                "1. Boil water\n2. Pour boiling water into cup of noodles\n3. Crack your egg into the cup of noodles"
        ));

        for (int i = 0; i < recipes.size(); i++) {
            recipes.get(i).setId((long) i);
        }
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

