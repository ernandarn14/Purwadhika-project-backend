package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RecipeCategoryRepo;
import com.pwd.kuekuapp.dao.RecipeIngredientsRepo;
import com.pwd.kuekuapp.dao.RecipeRepo;
import com.pwd.kuekuapp.dao.RecipeStepRepo;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.RecipeCategory;
//import com.pwd.kuekuapp.entity.RecipeSteps;
import com.pwd.kuekuapp.entity.Recipes;
//import com.pwd.kuekuapp.entity.RecipesIngredients;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {
	@Autowired
	private RecipeRepo recipeRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RecipeCategoryRepo recipeCategoryRepo;
	
	@Autowired
	private RecipeIngredientsRepo recipeIngredientsRepo;
	
	@Autowired 
	private RecipeStepRepo recipeStepRepo;

	@Override
	@Transactional
	public Iterable<Recipes> getAllRecipe() {
		return recipeRepo.findAll();
	}

	@Override
	@Transactional
	public Optional<Recipes> getRecipeById(int id) {
		return recipeRepo.findById(id);
	}

	@Override
	@Transactional
	public Iterable<Recipes> getRecipeByUser(int users, String sort) {
		if (sort.equals("asc")) {
			return recipeRepo.getRecipesByUserAsc(users);
		} else {
			return recipeRepo.getRecipesByUserDesc(users);
		}
	}

	@Override
	@Transactional
	public Recipes addRecipe(Recipes recipes, int recipeCategory, int users) {
//		Recipes findRecipe = recipeRepo.findById(recipeId).get();
//
//		if (findRecipe == null)
//			throw new RuntimeException("Resep Tidak Ditemukan");

		RecipeCategory findCategoryRecipe = recipeCategoryRepo.findById(recipeCategory).get();
		
		if (findCategoryRecipe == null)
			throw new RuntimeException("Kategori Tidak Ditemukan");
		recipes.setRecipeCategory(findCategoryRecipe);

		Users findUser = userRepo.findById(users).get();
		
		if (findUser == null)
			throw new RuntimeException("Pengguna Tidak Ditemukan");
		recipes.setId(0);
		recipes.setUsers(findUser);
		
		return recipeRepo.save(recipes);
	}

	@Override
	@Transactional
	public Recipes addCategoryRecipe(Recipes recipes, int recipeCategory) {
		RecipeCategory findCategoryRecipe = recipeCategoryRepo.findById(recipeCategory).get();
		recipes.setId(0);
		recipes.setRecipeCategory(findCategoryRecipe);
		return recipeRepo.save(recipes);
	}

	@Override
	@Transactional
	public Iterable<Recipes> getCategoryName(int id) {
		return recipeRepo.getRecipeCategory(id);
	}

	@Override
	@Transactional
	public Iterable<Recipes> getRecipeCategoryName() {
		return recipeRepo.getRecipeCategoryName();
	}

	@Override
	@Transactional
	public Page<Recipes> findByCategory(String categoryName, String sort, Pageable pageable) {
		if(sort.equals("asc")) {
			return recipeRepo.orderByCategoryAsc(categoryName, pageable);
		} 
		else {
			return recipeRepo.orderByCategoryDesc(categoryName, pageable);
		}
	}

	@Override
	@Transactional
	public Page<Recipes> getAllRecipePagination(String sort, Pageable pageable) {
		if(sort.equals("asc")) {
			return recipeRepo.orderRecipeByAsc(pageable);
		} else {
			return recipeRepo.orderRecipeByDesc(pageable);
		}
	}

	@Override
	@Transactional
	public Iterable<Recipes> getBestRecipe() {
		return recipeRepo.getBestRecipes();
	}

	@Override
	@Transactional
	public void deleteRecipes(int id) {
		Recipes findRecipe = recipeRepo.findById(id).get();
		if (findRecipe == null) {
			throw new RuntimeException("Resep tidak ditemukan");
		}
		
		//putus hubungan dengan user
		findRecipe.getUsers().setRecipes(null);
		findRecipe.setUsers(null);
		
		//putus hubungan dengan category
		findRecipe.getRecipeCategory().setRecipes(null);
		findRecipe.setRecipeCategory(null);
		
		//putus relasi dengan ingredients
		findRecipe.getRecipesIngredients().forEach(ingredients -> {
//			List<Recipes> listRecipe = ingredients.getRecipes();
			ingredients.setRecipes(null);
			recipeIngredientsRepo.save(ingredients);
		});
		findRecipe.setRecipesIngredients(null);
		//recipeIngredientsRepo.deleteById(id);
		//recipeIngredientsRepo.deleteIngredientByRecipe(id);
		
		//putus relasi dengan steps
		findRecipe.getRecipeSteps().forEach(steps -> {
			steps.setRecipes(null);
			recipeStepRepo.save(steps);
		});
		findRecipe.setRecipeSteps(null);
		//recipeStepRepo.deleteById(id);
		//recipeStepRepo.deleteStepByRecipes(id);
		
		recipeRepo.deleteById(id);
	}

	@Override
	@Transactional
	public Iterable<Recipes> getBestRecipesByCategory(String categoryName, String sort) {
		if(sort.equals("asc")) {
			return recipeRepo.getChartBestRecipesBycategoryAsc(categoryName);
		} 
		else {
			return recipeRepo.getChartBestRecipesBycategoryDesc(categoryName);
		}
	}

	@Override
	@Transactional
	public Iterable<Recipes> getAllBestRecipe(String sort) {
		if(sort.equals("asc")) {
			return recipeRepo.getChartBestRecipesAsc();
		} else {
			return recipeRepo.getChartBestRecipesDesc();
		}
	}

	@Override
	@Transactional
	public Iterable<Recipes> adminGetRecipeByCategory(String categoryName, String sort) {
		if(sort.equals("asc")) {
			return recipeRepo.getByCategoryAsc(categoryName);
		} 
		else {
			return recipeRepo.getByCategoryDesc(categoryName);
		}
	}

	@Override
	@Transactional
	public Iterable<Recipes> adminGetAllRecipe(String sort) {
		if(sort.equals("asc")) {
			return recipeRepo.getRecipeByAsc();
		} else {
			return recipeRepo.getRecipeByDesc();
		}
	}

	@Override
	@Transactional
	public Iterable<Recipes> getRecipeByCategoryUser(int users, String categoryName, String sort) {
		if(sort.equals("asc")) {
			return recipeRepo.getByCategoryUserAsc(categoryName, users);
		} else {
			return recipeRepo.getByCategoryUserDesc(categoryName, users);
		}
	}



}
