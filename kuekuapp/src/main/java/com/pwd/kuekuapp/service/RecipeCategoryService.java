package com.pwd.kuekuapp.service;

import com.pwd.kuekuapp.entity.RecipeCategory;

public interface RecipeCategoryService {
	public Iterable<RecipeCategory> getAllRecipeCategory();
	public Iterable<RecipeCategory> getCategoryByRecipe(int recipes);
	public RecipeCategory addRecipeCategory(RecipeCategory recipeCategory);
}
