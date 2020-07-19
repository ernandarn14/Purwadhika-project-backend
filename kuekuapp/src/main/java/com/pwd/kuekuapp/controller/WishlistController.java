package com.pwd.kuekuapp.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pwd.kuekuapp.dao.WishlistRepo;
import com.pwd.kuekuapp.entity.Wishlists;
import com.pwd.kuekuapp.service.WishlistService;

@RestController
@RequestMapping("/rencana")
@CrossOrigin
public class WishlistController {
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private WishlistRepo wishlistRepo;
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@GetMapping
	public Iterable<Wishlists> getAllWishlist(){
		return wishlistService.getAllWishlist();
	}
	
	@GetMapping("/{users}/{recipes}")
	public Iterable<Wishlists> getWishlistByUserRecipe(@PathVariable int users, @PathVariable int recipes){
		return wishlistService.getWishlistByUserRecipe(users, recipes);
	}
	
	@GetMapping("/cek-rencana")
	public Iterable<Wishlists> getUniqueWishlist(@RequestParam int userId, @RequestParam int recipeId){
		return wishlistRepo.getUniqueWishlist(userId, recipeId);
	}
	
	@GetMapping("/pengguna/{users}/{sort}")
	public Iterable<Wishlists> getWishlistByUser(@PathVariable int users, @PathVariable String sort){
		return wishlistService.getWishlistByUser(users, sort);
	}
	
	@GetMapping("/pengguna/{users}/kategori/{sort}")
	public Iterable<Wishlists> getWishlistByUser(@PathVariable int users, @PathVariable String sort, @RequestParam String categoryName){
		return wishlistService.getWishlistCategoryByUser(users, sort, categoryName);
	}
	
	@PostMapping("/tambah/pengguna/{users}/resep/{recipes}")
	public Wishlists addWishlist(@RequestBody Wishlists wishlists, @PathVariable int users, @PathVariable int recipes) {
		return wishlistService.addWishlist(wishlists, users, recipes);
	}
	
	@PutMapping
	public Wishlists updateWishlist(@RequestBody Wishlists wishlists) {
		return wishlistService.updateWishlist(wishlists);
	}
	
	@DeleteMapping("/hapus/{id}")
	public void deleteWishlist(@PathVariable int id) {
		wishlistService.deleteWishlist(id);
	}
	
	@GetMapping("/download/{fileName:.+}") // .+ extension .jpg/png
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;

		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Download");

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}
}
