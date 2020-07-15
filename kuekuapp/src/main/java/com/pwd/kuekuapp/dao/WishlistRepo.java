package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Wishlists;

public interface WishlistRepo extends JpaRepository<Wishlists, Integer> {
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id WHERE w.user_id = ?1 and w.recipe_id= ?2", nativeQuery = true)
	public Iterable<Wishlists> getUniqueWishlist(int userId, int recipeId);
	
	@Query(value = "SELECT * FROM Wishlists WHERE user_id = ?1", nativeQuery = true)
	public  Iterable<Wishlists> getWishlistByUsers(int users);
}
