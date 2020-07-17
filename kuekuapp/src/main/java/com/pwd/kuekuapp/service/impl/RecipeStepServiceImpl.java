package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RecipeRepo;
import com.pwd.kuekuapp.dao.RecipeStepRepo;
import com.pwd.kuekuapp.entity.RecipeSteps;
import com.pwd.kuekuapp.entity.Recipes;
import com.pwd.kuekuapp.service.RecipeStepService;

@Service
public class RecipeStepServiceImpl implements RecipeStepService {
	@Autowired
	private RecipeStepRepo recipeStepRepo;

	@Autowired
	private RecipeRepo recipeRepo;

	@Override
	@Transactional
	public Iterable<RecipeSteps> getAllSteps() {
		return recipeStepRepo.findAll();
	}

	@Override
	@Transactional
	public Iterable<RecipeSteps> getStepsByRecipeId(int recipes) {
		return recipeStepRepo.getAllStepByRecipes(recipes);
	}

	@Override
	@Transactional
	public RecipeSteps addRecipe(RecipeSteps recipeSteps, int recipes) {
		Recipes findRecipe = recipeRepo.findById(recipes).get();

		if (findRecipe == null)
			throw new RuntimeException("Resep Tidak Ditemukan");

		recipeSteps.setId(0);
		recipeSteps.setRecipes(findRecipe);

		return recipeStepRepo.save(recipeSteps);
	}

	@Override
	@Modifying
	@Transactional
	public void deleteStepByRecipe(int recipes) {
		RecipeSteps findStep = recipeStepRepo.findById(recipes).get();

		if (findStep == null) {
			throw new RuntimeException("Langkah dengan resepId tersebut tidak ditemukan");
		}

		findStep.getRecipes().setRecipeSteps(null);
		findStep.setRecipes(null);

		recipeStepRepo.deleteById(recipes);
	}

	@Override
	@Transactional
	public void deleteStepById(int id) {
		RecipeSteps findStep = recipeStepRepo.findById(id).get();

		if (findStep == null) {
			throw new RuntimeException("Langkah tidak ditemukan");
		}

		findStep.getRecipes().setRecipeSteps(null);
		findStep.setRecipes(null);

		recipeStepRepo.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<RecipeSteps> getStepsById(int id) {
		return recipeStepRepo.findById(id);
	}

	@Override
	@Transactional
	public RecipeSteps editRecipe(int id, int recipes) {
		RecipeSteps findStep = recipeStepRepo.findById(id).get();

		if (findStep == null) {
			throw new RuntimeException("Langkah tidak ditemukan");
		}

		Recipes findRecipe = recipeRepo.findById(recipes).get();

		if (findRecipe == null) {
			throw new RuntimeException("Resep tidak ditemukan");
		}
		findStep.setRecipes(findRecipe);
		findStep.setId(findStep.getId());

		return recipeStepRepo.save(findStep);
	}

	@Override
	@Transactional
	public RecipeSteps editRecipeSteps(RecipeSteps recipeSteps, int recipes) {
		RecipeSteps findStep = recipeStepRepo.findById(recipeSteps.getId()).get();
		if (findStep == null) {
			throw new RuntimeException("Langkah tidak ditemukan");
		}

		Recipes findRecipe = recipeRepo.findById(recipes).get();

		if (findRecipe == null) {
			throw new RuntimeException("Resep tidak ditemukan");
		}
		findStep.setRecipes(findRecipe);
		findStep.setId(recipeSteps.getId());

		return recipeStepRepo.save(recipeSteps);
	}
}
