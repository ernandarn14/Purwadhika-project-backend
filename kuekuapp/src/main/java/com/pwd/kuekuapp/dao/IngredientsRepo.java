package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Ingredients;

public interface IngredientsRepo extends JpaRepository<Ingredients, Integer> {
	@Query(value = "SELECT LOWER(ingredientName) FROM Ingredients WHERE ingredientName = ?1", nativeQuery = true)
	public Iterable<Ingredients> findByIngredientName(String ingredientName);

}
