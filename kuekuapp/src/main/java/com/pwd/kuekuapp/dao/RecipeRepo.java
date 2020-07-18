package com.pwd.kuekuapp.dao;

//import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pwd.kuekuapp.entity.Recipes;

public interface RecipeRepo extends JpaRepository<Recipes, Integer>,  PagingAndSortingRepository<Recipes, Integer> {
	public Optional<Recipes> findByRecipeName(String recipeName);
	
	@Query(value = "SELECT * FROM Recipes WHERE user_id = ?1", nativeQuery = true)
	public Iterable<Recipes> getRecipesByUser(int users);
	
	@Query(value = "SELECT * FROM Recipes order by rating desc limit 3", nativeQuery = true)
	public Iterable<Recipes> getBestRecipes();
	
	@Query(value = "SELECT * FROM Recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by rating desc limit 3", nativeQuery = true)
	public Iterable<Recipes> gettBestRecipesBycategory(String categoryName);
	
	@Query(value = "SELECT a.recipe_name, b.recipe_category_name FROM recipes a join recipe_category b on b.id = a.recipe_category_id WHERE a.recipe_id = ?1", nativeQuery = true)
	public Iterable<Recipes> getRecipeCategory(int id);
	
	@Query(value = "SELECT a.recipe_name, b.recipe_category_name FROM recipes a join recipe_category b on b.id = a.recipe_category_id", nativeQuery = true)
	public Iterable<Recipes> getRecipeCategoryName();
	
	
	//query pagination and filter page recipe
	@Query(value ="SELECT * FROM recipes order by recipe_name asc ", nativeQuery = true)
	public Page<Recipes> orderRecipeByAsc(Pageable pageable);
	
	@Query(value ="SELECT * FROM recipes order by recipe_name desc ", nativeQuery = true)
	public Page<Recipes> orderRecipeByDesc(Pageable pageable);
	
	@Query(value ="SELECT * FROM recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by recipe_name asc ", nativeQuery = true)
	public Page<Recipes> orderByCategoryAsc(String categoryName, Pageable pageable);
	
	@Query(value ="SELECT * FROM recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by recipe_name desc ", nativeQuery = true)
	public Page<Recipes> orderByCategoryDesc(String categoryName, Pageable pageable);
//	
//	@Query(value ="SELECT distinct * FROM recipe_category rc join recipes r on r.recipe_category_id = rc.id where rc.recipe_category_name=?1 and r.id=?2 order by recipe_name asc ", nativeQuery = true)
//	public List<Recipes> orderByCategory(String categoryName, int id);
	
	
	//query chart recipe report
	@Query(value = "SELECT * FROM Recipes order by rating desc", nativeQuery = true)
	public Iterable<Recipes> getChartBestRecipesDesc();
	
	@Query(value = "SELECT * FROM Recipes order by rating asc", nativeQuery = true)
	public Iterable<Recipes> getChartBestRecipesAsc();
	
	@Query(value = "SELECT * FROM Recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by rating desc", nativeQuery = true)
	public Iterable<Recipes> getChartBestRecipesBycategoryDesc(String categoryName);
	
	@Query(value = "SELECT * FROM Recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by rating asc", nativeQuery = true)
	public Iterable<Recipes> getChartBestRecipesBycategoryAsc(String categoryName);
	
	
	
	//query filter dashboard recipe
	@Query(value ="SELECT * FROM recipes order by recipe_name asc ", nativeQuery = true)
	public Iterable<Recipes> getRecipeByAsc();
	
	@Query(value ="SELECT * FROM recipes order by recipe_name desc ", nativeQuery = true)
	public Iterable<Recipes> getRecipeByDesc();
	
	@Query(value ="SELECT * FROM recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by recipe_name asc ", nativeQuery = true)
	public Iterable<Recipes> getByCategoryAsc(String categoryName);
	
	@Query(value ="SELECT * FROM recipes r join recipe_category rc on rc.id = r.recipe_category_id where rc.recipe_category_name=?1 order by recipe_name desc ", nativeQuery = true)
	public Iterable<Recipes> getByCategoryDesc(String categoryName);
}
