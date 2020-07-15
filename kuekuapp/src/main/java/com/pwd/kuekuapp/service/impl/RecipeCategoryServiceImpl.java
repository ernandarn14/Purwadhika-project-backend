package com.pwd.kuekuapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RecipeCategoryRepo;
import com.pwd.kuekuapp.entity.RecipeCategory;
import com.pwd.kuekuapp.service.RecipeCategoryService;

@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {
	@Autowired
	private RecipeCategoryRepo recipeCategoryRepo;

	@Override
	@Transactional
	public Iterable<RecipeCategory> getAllRecipeCategory() {
		
		return recipeCategoryRepo.findAll();
	}

	@Override
	@Transactional
	public RecipeCategory addRecipeCategory(RecipeCategory recipeCategory) {
		recipeCategory.setId(0);
		return recipeCategoryRepo.save(recipeCategory);
	}

	@Override
	@Transactional
	public Iterable<RecipeCategory> getCategoryByRecipe(int recipes) {
		return recipeCategoryRepo.getCategoryByRecipes(recipes);
	}
	
	

}
