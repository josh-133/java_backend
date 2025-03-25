package com.dewc.gdp_recipe_book;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * File to test RecipeController CRUD API requests
 */
@AutoConfigureMockMvc
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    /**
     * Test getting all recipes
     * @throws Exception if the request fails
     */
    @Test
    void testGetRecipes() throws Exception {
        List<Recipe> recipes = Arrays.asList(
            new Recipe("Spaghetti", "Tomato, Pasta", "Pasta with tomato sauce"),
            new Recipe("Tacos", "Tortilla, Meat", "Tortilla with meat")
        );

        when(recipeRepository.findAll()).thenReturn(recipes);

        mockMvc.perform(get("/recipes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].title").value("Spaghetti"));
    }

    /**
     * Test getting a specific recipe
     * @throws Exception if the requests fail
     */
    @Test
    void testGetRecipeById() throws Exception {
        Recipe recipe = new Recipe("Spaghetti", "Tomato, Pasta", "Boil pasta, add sauce");
        
        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/recipes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Spaghetti"));
    }

    /**
     * Tests creating a recipe
     * @throws Exception if the requests fails
     */
    @Test
    void testCreateRecipe() throws Exception {
        Recipe newRecipe = new Recipe("Burger", "Burger, Bun, Patty", "Grill patty");

        when(recipeRepository.save(any(Recipe.class))).thenReturn(newRecipe);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Burger\", \"ingredients\": \"Bun, Patty\", \"method\": \"Grill patty\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Burger"));
    }

    /**
     * Tests updating a specific recipe
     * @throws Exception if the request fails
     */
    @Test
    void testUpdateRecipe() throws Exception {
        Recipe existingRecipe = new Recipe("Tacos", "Tortilla, Meat", "Cook meat, serve in tortilla");
        Recipe updatedRecipe = new Recipe("Tacos", "Tortilla, Meat, Cheese", "Cook meat, add cheese, serve");

        when(recipeRepository.findById("1")).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        mockMvc.perform(post("/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Taco\", \"ingredients\": \"Tortilla, Meat, Cheese\", \"method\": \"Cook meat, add cheese, serve\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients").value("Tortilla, Meat, Cheese"));
    }

    /**
     * Tests deleting a recipe
     * @throws Exception if the request fails
     */
    @Test
    void testDeleteRecipe() throws Exception {
        Recipe badRecipe = new Recipe("Salad", "Lettuce, Tomato", "Mix together");

        when(recipeRepository.findById("1")).thenReturn(Optional.of(badRecipe));
        doNothing().when(recipeRepository).deleteById("1");

        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Salad"));
    }
}
