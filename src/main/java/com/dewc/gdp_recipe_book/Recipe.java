package com.dewc.gdp_recipe_book;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a recipe entity stored in the MongoDB database.
 */
@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String title;
    private String ingredients;
    private String method;

    /**
     * Default constructor for Recipe.
     * Initialises a new Recipe with no values.
     */
    public Recipe() {

    }

    /**
     * Constructs a new Recipe with the specified details.
     * @param title         The title of the recipe.
     * @param ingredients   The ingredients required for the recipe.
     * @param method        The method or instructions for preparing the recipe.
     */
    public Recipe(String title, String ingredients, String method) {
        this.title = title;
        this.ingredients = ingredients;
        this.method = method;
    }

    /**
     * Gets the unique identifier of the recipe.
     * 
     * @return The recipe ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the recipe
     * @param id The new Recipe ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the recipe.
     * 
     * @return The recipe title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recipe
     * @param title The new Recipe Title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the ingredients of the recipe.
     * 
     * @return The ingredients of the recipe.
     */
    public String getIngredients() {
        return ingredients;        
    }

    /**
     * Sets the ingredients of the recipe
     * @param ingredients The new Recipe Ingredients.
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Gets the method of the recipe.
     * 
     * @return The recipe method.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method of the recipe
     * @param method The new Recipe Method.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Computes the hash code for this Recipe.
     * 
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        return result;
    }

    /**
     * Compares this recipe to another object for equality.
     * 
     * @param obj The object to compare.
     * @return {@code true} if the objects are equal, otherwise {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recipe other = (Recipe) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (!ingredients.equals(other.ingredients))
            return false;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        return true;
    }

    /**
     * Return a string representation of the recipe.
     * 
     * @return A string containing the recipe details.
     */
    @Override
    public String toString() {
        return "Recipe [id=" + id + ", title=" + title + ", ingredients=" + ingredients + ", method=" + method + "]";
    }
}
