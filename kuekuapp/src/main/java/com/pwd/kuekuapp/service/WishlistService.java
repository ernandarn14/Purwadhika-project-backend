package com.pwd.kuekuapp.service;

import com.pwd.kuekuapp.entity.Wishlists;

public interface WishlistService {
	public Iterable<Wishlists> getAllWishlist();
	public Iterable<Wishlists> getWishlistByUser(int users, String sort);
	public Iterable<Wishlists> getWishlistCategoryByUser(int users, String sort, String categoryName);
	public Iterable<Wishlists> getWishlistByUserRecipe(int userId, int recipeId);
	public Wishlists addWishlist(Wishlists wishlists, int users, int recipes);
	public Wishlists updateWishlist(Wishlists wishlists);
	public void deleteWishlist(int id);
}
