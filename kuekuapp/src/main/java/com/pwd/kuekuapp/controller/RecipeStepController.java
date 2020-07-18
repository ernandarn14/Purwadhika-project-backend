package com.pwd.kuekuapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.dao.RecipeStepRepo;
import com.pwd.kuekuapp.entity.RecipeSteps;
import com.pwd.kuekuapp.service.RecipeStepService;

@RestController
@RequestMapping("/langkah-membuat")
@CrossOrigin
public class RecipeStepController {
	@Autowired
	private RecipeStepService recipeStepService;
	
	@Autowired
	private RecipeStepRepo recipeStepRepo;
	
	@GetMapping
	public Iterable<RecipeSteps> getAllSteps(){
		return recipeStepService.getAllSteps();
	}
	
	@GetMapping("/{id}")
	public Optional<RecipeSteps> getStepsById(@PathVariable int id){
		return recipeStepService.getStepsById(id);
	}
	
	@GetMapping("/resep/{recipes}")
	public Iterable<RecipeSteps> getStepsByRecipeId(@PathVariable int recipes){
		return recipeStepService.getStepsByRecipeId(recipes);
	}
	
	@PostMapping("/tambah/resep/{recipes}")
	public RecipeSteps addRecipe(@RequestBody RecipeSteps recipeSteps, @PathVariable int recipes) {
		return recipeStepService.addRecipe(recipeSteps, recipes);
	}
	
	@PutMapping("/edit/{id}/{recipes}")
	public RecipeSteps editRecipe(@PathVariable int id, @PathVariable int recipes) {
		return recipeStepService.editRecipe(id, recipes);
	}
	
	@PutMapping("/edit/{recipes}")
	public RecipeSteps editRecipeStep(@RequestBody RecipeSteps recipeSteps, @PathVariable int recipes) {
		return recipeStepService.editRecipeSteps(recipeSteps, recipes);
	}
	
	@DeleteMapping("/hapus/resep")
	public void deleteStepByRecipe(@RequestParam int recipes) {
		recipeStepService.deleteStepByRecipe(recipes);
		//recipeStepRepo.deleteStepByRecipes(recipeId);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteStepsById(@PathVariable int id) {
		recipeStepService.deleteStepById(id);
	}
	
}
