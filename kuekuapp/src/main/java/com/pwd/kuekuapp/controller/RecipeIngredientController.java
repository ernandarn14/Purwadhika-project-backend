package com.pwd.kuekuapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.entity.RecipesIngredients;
import com.pwd.kuekuapp.service.RecipeIngredientService;

@RestController
@RequestMapping("/bahan")
@CrossOrigin
public class RecipeIngredientController {
	@Autowired
	private RecipeIngredientService recipeIngredientService;
	
	@GetMapping
	public Iterable<RecipesIngredients> getAllIngredients(){
		return recipeIngredientService.getAllIngredients();
	}
	
	@GetMapping("/resep/{recipes}")
	public Iterable<RecipesIngredients> getIngredientByRecipeId(@PathVariable int recipes){
		return recipeIngredientService.getIngredientByRecipeId(recipes);
	}
	
	@PostMapping("/tambah/resep/{recipes}")
	public RecipesIngredients addIngredients(@RequestBody RecipesIngredients recipesIngredients, @PathVariable int recipes) {
		return recipeIngredientService.addIngredients(recipesIngredients, recipes);
	}
	
	@PutMapping("/edit/{id}/{recipes}")
	public RecipesIngredients editIngredients(@PathVariable int id, @PathVariable int recipes) {
		return recipeIngredientService.editIngredients(id, recipes);
	}
	
	@DeleteMapping("/{id}")
	public void deleteIngredients(@PathVariable int id) {
		 recipeIngredientService.deleteIngredients(id);
	}
}
