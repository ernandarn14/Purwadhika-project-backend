package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RecipeIngredientsRepo;
import com.pwd.kuekuapp.dao.RecipeRepo;
import com.pwd.kuekuapp.entity.Recipes;
import com.pwd.kuekuapp.entity.RecipesIngredients;
import com.pwd.kuekuapp.service.RecipeIngredientService;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {
	@Autowired
	private RecipeIngredientsRepo recipeIngredientsRepo;
	
	@Autowired
	private RecipeRepo recipeRepo;

	@Override
	public Iterable<RecipesIngredients> getAllIngredients() {
		return recipeIngredientsRepo.findAll();
	}

	@Override
	@Transactional
	public Iterable<RecipesIngredients> getIngredientByRecipeId(int recipes) {
		return recipeIngredientsRepo.getAllIngredientByUsers(recipes);
	}

	@Override
	@Transactional
	public RecipesIngredients addIngredients(RecipesIngredients recipesIngredients, int recipes) {
		Recipes findRecipe = recipeRepo.findById(recipes).get();

		if (findRecipe == null)
			throw new RuntimeException("Resep Tidak Ditemukan");
		
		recipesIngredients.setId(0);
		recipesIngredients.setRecipes(findRecipe);
		
		return recipeIngredientsRepo.save(recipesIngredients);
	}

	@Override
	@Transactional
	public void deleteIngredients(int id) {
		RecipesIngredients findIngredient = recipeIngredientsRepo.findById(id).get();
		if (findIngredient == null)
			throw new RuntimeException("Bahan Resep Tidak Ditemukan");
		
		findIngredient.getRecipes().setRecipesIngredients(null);
		findIngredient.setRecipes(null);
		
		recipeIngredientsRepo.deleteById(id);
	}

	@Override
	@Transactional
	public RecipesIngredients editIngredients(int id, int recipes) {
		RecipesIngredients findIngredient = recipeIngredientsRepo.findById(id).get();
		if (findIngredient == null)
			throw new RuntimeException("Bahan Resep Tidak Ditemukan");
		
		Recipes findRecipe = recipeRepo.findById(recipes).get();
		
		if (findRecipe == null) {
			throw new RuntimeException("Resep tidak ditemukan");
		}
		findIngredient.setRecipes(findRecipe);
		
		return recipeIngredientsRepo.save(findIngredient);
	}

	@Override
	@Transactional
	public RecipesIngredients editIngredients(RecipesIngredients recipesIngredients, int recipes) {
		RecipesIngredients findIngredient = recipeIngredientsRepo.findById(recipesIngredients.getId()).get();
		if (findIngredient == null)
			throw new RuntimeException("Bahan Resep Tidak Ditemukan");
		
		Recipes findRecipe = recipeRepo.findById(recipes).get();
		
		if (findRecipe == null) {
			throw new RuntimeException("Resep tidak ditemukan");
		}
		
		findIngredient.setRecipes(findRecipe);
		findIngredient.setId(recipesIngredients.getId());
		
		return recipeIngredientsRepo.save(recipesIngredients);
	}

	@Override
	@Transactional
	public Optional<RecipesIngredients> getIngredientsById(int id) {
		return recipeIngredientsRepo.findById(id);
	}

}
