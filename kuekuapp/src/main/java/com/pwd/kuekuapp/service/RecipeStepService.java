package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.RecipeSteps;

public interface RecipeStepService {
	public Iterable<RecipeSteps> getAllSteps();
	public Optional<RecipeSteps> getStepsById(int id);
	public Iterable<RecipeSteps> getStepsByRecipeId(int recipes);
	public RecipeSteps addRecipe(RecipeSteps recipeSteps, int recipes);
	public RecipeSteps editRecipe(int id, int recipes);
	public RecipeSteps editRecipeSteps(RecipeSteps recipeSteps, int recipes);
	public void deleteStepById(int id);
	public void deleteStepByRecipe(int recipes);
}
