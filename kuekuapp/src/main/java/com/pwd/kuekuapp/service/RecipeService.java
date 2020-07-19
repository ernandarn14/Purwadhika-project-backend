package com.pwd.kuekuapp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pwd.kuekuapp.entity.Recipes;

public interface RecipeService {
	public Iterable<Recipes> getAllRecipe();
	public Iterable<Recipes> getBestRecipe();
	public Optional<Recipes> getRecipeById(int id);
	public Iterable<Recipes> getRecipeByUser(int users, String sort);
	public Iterable<Recipes> getRecipeByCategoryUser(int users, String categoryName, String sort);
	public Iterable<Recipes> getCategoryName(int id);
	public Iterable<Recipes> getRecipeCategoryName();
	public Recipes addRecipe(Recipes recipes, int recipeCategory, int users);
	public Recipes addCategoryRecipe(Recipes recipes, int recipeCategory);
	public Page<Recipes> findByCategory(String categoryName, String sort, Pageable pageable);
	public Page<Recipes> getAllRecipePagination(String sort, Pageable pageable);
	public Iterable<Recipes> getBestRecipesByCategory(String categoryName, String sort);
	public Iterable<Recipes> getAllBestRecipe(String sort);
	public Iterable<Recipes> adminGetRecipeByCategory(String categoryName, String sort);
	public Iterable<Recipes> adminGetAllRecipe(String sort);
	public void deleteRecipes(int id);
}
