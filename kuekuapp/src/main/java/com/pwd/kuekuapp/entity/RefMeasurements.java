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
public class RefMeasurements {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String measurementName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "refMeasurements", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RecipeIngredients> recipeIngredients;

	public List<RecipeIngredients> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredients> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeasurementName() {
		return measurementName;
	}

	public void setMeasurementName(String measurementName) {
		this.measurementName = measurementName;
	}
}
