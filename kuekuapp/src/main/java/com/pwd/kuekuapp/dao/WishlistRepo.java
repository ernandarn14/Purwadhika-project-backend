package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Wishlists;

public interface WishlistRepo extends JpaRepository<Wishlists, Integer> {
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id WHERE w.user_id = ?1 and w.recipe_id= ?2", nativeQuery = true)
	public Iterable<Wishlists> getUniqueWishlist(int userId, int recipeId);
	
	
	//filter user menu 
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id WHERE w.user_id = ?1 order by r.recipe_name asc", nativeQuery = true)
	public  Iterable<Wishlists> getWishlistByUsersAsc(int users);
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id WHERE w.user_id = ?1 order by r.recipe_name desc", nativeQuery = true)
	public  Iterable<Wishlists> getWishlistByUserDesc(int users);
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id join recipe_category rc on rc.id = r.recipe_category_id WHERE w.user_id = ?1 and rc.recipe_category_name=?2 order by r.recipe_name asc", nativeQuery = true)
	public  Iterable<Wishlists> getWishlistCategoryByUsersAsc(int users, String categoryName);
	@Query(value = "SELECT * FROM Wishlists w join recipes r on r.id = w.recipe_id join recipe_category rc on rc.id = r.recipe_category_id WHERE w.user_id = ?1 and rc.recipe_category_name=?2 order by r.recipe_name desc", nativeQuery = true)
	public  Iterable<Wishlists> getWishlistCategoryByUsersDesc(int users, String categoryName);
}
