package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.RecipesIngredients;

public interface RecipeIngredientService {
	public Iterable<RecipesIngredients> getAllIngredients();
	public Optional<RecipesIngredients> getIngredientsById(int id);
	public Iterable<RecipesIngredients> getIngredientByRecipeId(int recipes);
	public RecipesIngredients addIngredients(RecipesIngredients recipesIngredients, int recipes);
	public RecipesIngredients editIngredients(int id, int recipes);
	public RecipesIngredients editIngredients(RecipesIngredients recipesIngredients, int recipes);
	public void deleteIngredients(int id);
}
