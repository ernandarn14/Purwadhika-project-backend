package com.pwd.kuekuapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.RecipeCategory;

public interface RecipeCategoryRepo extends JpaRepository<RecipeCategory, Integer> {
	public Optional<RecipeCategory> findByRecipeCategoryName(String recipeCategoryName);
	
	@Query(value = "SELECT a.recipe_name, b.recipe_category_name FROM recipes a join recipe_category b on b.id = a.recipe_category_id WHERE b.recipe_id = ?1", nativeQuery = true)
	public Iterable<RecipeCategory> getCategoryByRecipes(int recipes);
}
