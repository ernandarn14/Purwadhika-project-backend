package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.RecipeSteps;

public interface RecipeStepRepo extends JpaRepository<RecipeSteps, Integer> {
	@Query(value = "SELECT * FROM recipe_steps WHERE recipe_id = ?1", nativeQuery = true)
	public RecipeSteps getStepByRecipes(int recipes);
	
	@Query(value = "SELECT * FROM recipe_steps WHERE recipe_id = ?1", nativeQuery = true)
	public Iterable<RecipeSteps> getAllStepByRecipes(int recipes);
	
	@Query(value = "Delete from recipe_steps where recipe_id= ?1", nativeQuery = true)
	public void deleteStepByRecipes(int recipes);
	
	public void deleteByRecipes(int recipes);
}
