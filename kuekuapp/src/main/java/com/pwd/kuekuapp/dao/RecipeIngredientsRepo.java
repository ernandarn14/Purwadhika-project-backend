package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.RecipesIngredients;

public interface RecipeIngredientsRepo extends JpaRepository<RecipesIngredients, Integer> {
	@Query(value = "SELECT * FROM recipes_ingredients WHERE recipe_id = ?1", nativeQuery = true)
	public RecipesIngredients getIngredientByUsers(int recipes);
	
	@Query(value = "SELECT * FROM recipes_ingredients WHERE recipe_id = ?1", nativeQuery = true)
	public Iterable<RecipesIngredients> getAllIngredientByUsers(int recipes);
	
	@Query(value = "Delete from recipes_ingredients where recipe_id= ?1", nativeQuery = true)
	public RecipesIngredients deleteIngredientByRecipe(int recipes);
}
