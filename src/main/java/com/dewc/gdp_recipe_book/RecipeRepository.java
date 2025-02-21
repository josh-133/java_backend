package com.dewc.gdp_recipe_book;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecipeRepository extends JpaRepository<Object, Long> {
    
}
