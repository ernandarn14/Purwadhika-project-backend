package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.RecipeRepo;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.dao.WishlistRepo;
import com.pwd.kuekuapp.entity.Recipes;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.entity.Wishlists;
import com.pwd.kuekuapp.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {
	@Autowired
	private WishlistRepo wishlistRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired 
	private RecipeRepo recipeRepo;
	
	@Override
	@Transactional
	public Iterable<Wishlists> getAllWishlist() {
		return wishlistRepo.findAll();
	}

	@Override
	@Transactional
	public Wishlists updateWishlist(Wishlists wishlists) {
		Optional<Wishlists> findById = wishlistRepo.findById(wishlists.getId());

		if (findById.get() == null) {
			throw new RuntimeException("Resep belum tersimpan");
		}

		return wishlistRepo.save(wishlists);
	}

	@Override
	@Transactional
	public void deleteWishlist(int id) {
		Wishlists findById = wishlistRepo.findById(id).get();

		if (findById == null) {
			throw new RuntimeException("Resep belum tersimpan");
		}
		
		findById.getUsers().setWishlist(null);
		findById.setUsers(null);
		
		findById.getRecipes().setWishlist(null);
		findById.setRecipes(null);
		
		wishlistRepo.deleteById(id);
	}

	@Override
	@Transactional
	public Iterable<Wishlists> getWishlistByUserRecipe(int userId, int recipeId) {
		return wishlistRepo.getUniqueWishlist(userId, recipeId);
	}

	@Override
	@Transactional
	public Iterable<Wishlists> getWishlistByUser(int users, String sort) {
		if (sort.equals("asc")) {
			return wishlistRepo.getWishlistByUsersAsc(users);
		} else {
			return wishlistRepo.getWishlistByUserDesc(users);
		}
	}

	@Override
	@Transactional
	public Wishlists addWishlist(Wishlists wishlists, int users, int recipes) {
	Users findUser = userRepo.findById(users).get();
		
		if (findUser == null) {
			throw new RuntimeException("Pengguna Tidak Ditemukan");
		}
		
		Recipes findRecipe = recipeRepo.findById(recipes).get();
		if (findRecipe == null) {
			throw new RuntimeException("Resep Tidak Ditemukan");
		}
		
		wishlists.setUsers(findUser);
		wishlists.setRecipes(findRecipe);
		
		return wishlistRepo.save(wishlists);
	}

	@Override
	@Transactional
	public Iterable<Wishlists> getWishlistCategoryByUser(int users, String sort, String categoryName) {
		if (sort.equals("asc")) {
			return wishlistRepo.getWishlistCategoryByUsersAsc(users, categoryName);
		} else {
			return wishlistRepo.getWishlistCategoryByUsersDesc(users, categoryName);
		}
	}

}
