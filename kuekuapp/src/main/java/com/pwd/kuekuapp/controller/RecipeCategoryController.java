package com.pwd.kuekuapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.dao.RecipeCategoryRepo;
import com.pwd.kuekuapp.entity.RecipeCategory;
import com.pwd.kuekuapp.entity.Recipes;
import com.pwd.kuekuapp.service.RecipeCategoryService;

@RestController
@RequestMapping("/kategori-resep")
@CrossOrigin
public class RecipeCategoryController {
	@Autowired
	private RecipeCategoryService recipeCategoryService;
	
	@Autowired
	private RecipeCategoryRepo recipeCategoryRepo;
	
	@GetMapping
	public Iterable<RecipeCategory> getAllRecipeCategory(){
		return recipeCategoryService.getAllRecipeCategory();
	}
	
	@PostMapping
	public RecipeCategory addRecipeCategory(@RequestBody RecipeCategory recipeCategory) {
		return recipeCategoryService.addRecipeCategory(recipeCategory);
	}
	
	@GetMapping("/resep/{recipes}")
	public Iterable<RecipeCategory> getCategoryByRecipe(@PathVariable int recipes){
		return recipeCategoryService.getCategoryByRecipe(recipes);
	}
	
	@GetMapping("/{id}/resep")
	public List<Recipes> getRecipeCategory(@PathVariable int id){
		RecipeCategory findCategoryRecipe = recipeCategoryRepo.findById(id).get();
		if (findCategoryRecipe == null)
			 throw new RuntimeException("Kategori Tidak Ditemukan");
		return findCategoryRecipe.getRecipes();
	}

}
