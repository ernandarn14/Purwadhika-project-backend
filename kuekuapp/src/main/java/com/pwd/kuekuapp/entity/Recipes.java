package com.pwd.kuekuapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Recipes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String recipeName;
	
	private int rating;
	
	private String cookTime;
	
	private String numbServings;
	
	private String recipeImage;
	
	private Date uploadDate;
	
	private String shortDesc;
	
	private String postOption;
	
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private Users users;
	
	@ManyToOne()
	@JoinColumn(name = "recipe_category_id")
	private RecipeCategory recipeCategory;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "recipes")
	@JsonIgnore
	private List<RecipeIngredients> recipeIngredients;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipes")
	@JsonIgnore
	private List<RecipesIngredients> recipesIngredients;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipes")
	@JsonIgnore
	private List<RecipeSteps> recipeSteps;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipes", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Wishlists> wishlist;

	public String getPostOption() {
		return postOption;
	}

	public void setPostOption(String postOption) {
		this.postOption = postOption;
	}

	public List<RecipesIngredients> getRecipesIngredients() {
		return recipesIngredients;
	}

	public void setRecipesIngredients(List<RecipesIngredients> recipesIngredients) {
		this.recipesIngredients = recipesIngredients;
	}

	public List<Wishlists> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Wishlists> wishlist) {
		this.wishlist = wishlist;
	}

	public List<RecipeSteps> getRecipeSteps() {
		return recipeSteps;
	}

	public void setRecipeSteps(List<RecipeSteps> recipeSteps) {
		this.recipeSteps = recipeSteps;
	}

	public List<RecipeIngredients> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(List<RecipeIngredients> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public RecipeCategory getRecipeCategory() {
		return recipeCategory;
	}

	public void setRecipeCategory(RecipeCategory recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public String getNumbServings() {
		return numbServings;
	}

	public void setNumbServings(String numbServings) {
		this.numbServings = numbServings;
	}

	public String getRecipeImage() {
		return recipeImage;
	}

	public void setRecipeImage(String recipeImage) {
		this.recipeImage = recipeImage;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
}
