package com.pwd.kuekuapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RecipeCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String recipeCategoryName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "recipeCategory")
	@JsonIgnore
	private List<Recipes> recipes;
	

	public List<Recipes> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipes> recipes) {
		this.recipes = recipes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecipeCategoryName() {
		return recipeCategoryName;
	}

	public void setRecipeCategoryName(String recipeCategoryName) {
		this.recipeCategoryName = recipeCategoryName;
	}

}
