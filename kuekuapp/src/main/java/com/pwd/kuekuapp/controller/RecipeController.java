package com.pwd.kuekuapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwd.kuekuapp.dao.RecipeCategoryRepo;
import com.pwd.kuekuapp.dao.RecipeRepo;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.RecipeCategory;
import com.pwd.kuekuapp.entity.Recipes;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.RecipeService;

@RestController
@RequestMapping("/resep")
@CrossOrigin
public class RecipeController {
	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeRepo recipeRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RecipeCategoryRepo recipeCategoryRepo;

//	@Autowired
//	private RecipeCategoryRepo recipeCategoryRepo;

	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

	@GetMapping
	public Iterable<Recipes> getAllRecipe() {
		return recipeService.getAllRecipe();
	}

	@GetMapping("/{id}")
	public Optional<Recipes> getRecipeById(@PathVariable int id) {
		return recipeService.getRecipeById(id);
	}

	@GetMapping("/pengguna/{users}")
	public Iterable<Recipes> getRecipeByUser(@PathVariable int users) {
		return recipeService.getRecipeByUser(users);
	}

	@GetMapping("/{id}/kategori")
	public Iterable<Recipes> getCategoryName(@PathVariable int id) {
		return recipeService.getCategoryName(id);
	}
	
	@GetMapping("/kategori")
	public Iterable<Recipes> getRecipeCategoryName() {
		return recipeService.getRecipeCategoryName();
	}

	@PostMapping("/tambah/pengguna/{users}")
	public Recipes addRecipeByUser(@RequestParam("file") MultipartFile file,
			@RequestParam("userData") String stringRecipes, @PathVariable int users)
			throws JsonMappingException, JsonProcessingException {
		Date date = new Date();

		Recipes recipe = new ObjectMapper().readValue(stringRecipes, Recipes.class);
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "RECIPE-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);

		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resep/download/")
				.path(fileName).toUriString();

		recipe.setRecipeImage(fileDownloadUri);
		recipe.setId(0);

		Users findUser = userRepo.findById(users).get();
		recipe.setUsers(findUser);

		return recipeRepo.save(recipe);
	}

	@PostMapping("/tambah/pengguna/{users}/kategori/{recipeCategory}")
	public Recipes addRecipe(@RequestParam("file") MultipartFile file, @RequestParam("userData") String stringRecipes,
			@PathVariable int recipeCategory, @PathVariable int users)
			throws JsonMappingException, JsonProcessingException {
		Date date = new Date();

		Recipes recipe = new ObjectMapper().readValue(stringRecipes, Recipes.class);
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "RECIPE-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);

		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resep/download/")
				.path(fileName).toUriString();

		recipe.setRecipeImage(fileDownloadUri);
		recipe.setId(0);

		Users findUser = userRepo.findById(users).get();
		recipe.setUsers(findUser); 
		
		RecipeCategory findCategoryRecipe = recipeCategoryRepo.findById(recipeCategory).get();
		
		if (findCategoryRecipe == null)
			throw new RuntimeException("Kategori Tidak Ditemukan");
		recipe.setRecipeCategory(findCategoryRecipe);
		
		//recipeRepo.save(recipe);

		return recipeRepo.save(recipe);
	}
	
	@PutMapping("/edit/{id}/pengguna/{users}/kategori/{recipeCategory}")
	public Recipes editRecipe(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("userData") String stringRecipes,
			@PathVariable int recipeCategory, @PathVariable int users, @PathVariable int id)
			throws JsonMappingException, JsonProcessingException {
//		Recipes findRecipe = recipeRepo.findById(id).get(); 
		
		Date date = new Date();

		Recipes findRecipe = new ObjectMapper().readValue(stringRecipes, Recipes.class);
		String fileDownloadUri = findRecipe.getRecipeImage();
		
		if(file.toString() != "Optional.empty") {
			String fileExtension = file.get().getContentType().split("/")[1];
			String newFileName = "RECIPE-" + date.getTime() + "." + fileExtension;

			String fileName = StringUtils.cleanPath(newFileName);

			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resep/download/")
					.path(fileName).toUriString();
		}

		findRecipe.setRecipeImage(fileDownloadUri);
		

		Users findUser = userRepo.findById(users).get();
		findRecipe.setUsers(findUser); 
		
		RecipeCategory findCategoryRecipe = recipeCategoryRepo.findById(recipeCategory).get();
		
		if (findCategoryRecipe == null)
			throw new RuntimeException("Kategori Tidak Ditemukan");
		findRecipe.setRecipeCategory(findCategoryRecipe);
		
		findRecipe.setId(id);

		return recipeRepo.save(findRecipe);
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

		//System.out.println("Download");

		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@PostMapping("/tambah/kategori/{recipeCategory}/pengguna/{users}")
	public Recipes addCategoryRecipe(@RequestBody Recipes recipes, @PathVariable int recipeCategory, @PathVariable int users) {
		//return recipeService.addCategoryRecipe(recipes, recipeCategory);
		return recipeService.addRecipe(recipes, recipeCategory, users);
	}
	
	@GetMapping("/paging/{sort}")
	public Page<Recipes> getDataPaging(@RequestParam String categoryName, @PathVariable String sort, Pageable pageable){
		return recipeService.findByCategory(categoryName, sort, pageable);
	}
	
//	@GetMapping("/paging/{sort}")
//	public List<Page<Recipes>> getDataPaging(@RequestParam int id, @PathVariable String sort, Pageable pageable){
//		return recipeRepo.orderByCategoryDesc(id, pageable);
//	}
	
	@GetMapping("/sort/{sort}")
	public Page<Recipes> getAllDataPaging(@PathVariable String sort, Pageable pageable){
		return recipeService.getAllRecipePagination(sort, pageable);
	}
	
	@GetMapping("/terbaik")
	public Iterable<Recipes> getBestRecipe(){
		return recipeService.getBestRecipe();
	}
	
	@GetMapping("/list/{id}")
	public List<Recipes> getDataCategory(@PathVariable int id, @RequestParam String categoryName){
		return recipeRepo.orderByCategory(categoryName, id);
	}
	
	@DeleteMapping("/hapus/{id}")
	public void deleteRecipes(@PathVariable int id) {
		 recipeService.deleteRecipes(id);
	}
	
	@GetMapping("/coba/{id}")
	public void getRecipe(@PathVariable int id) {
		Recipes findRecipe = recipeRepo.findById(id).get();
		System.out.println(findRecipe);
	}
	
	@PutMapping("/tambah/nilai/{id}")
	public Recipes addScore(@PathVariable int id, @RequestBody Recipes recipe) {
		Recipes findRecipe = recipeRepo.findById(id).get(); 
		if(findRecipe == null)
			throw new RuntimeException("Resep Tidak Ditemukan");
		recipe.setId(id);
		recipe.setRecipeName(findRecipe.getRecipeName());
		recipe.setCookTime(findRecipe.getCookTime());
		recipe.setNumbServings(findRecipe.getNumbServings());
		recipe.setRecipeImage(findRecipe.getRecipeImage());
		recipe.setShortDesc(findRecipe.getShortDesc());
		recipe.setUploadDate(findRecipe.getUploadDate());
		recipe.setUsers(findRecipe.getUsers());
		recipe.setRecipeCategory(findRecipe.getRecipeCategory());
		recipe.setPostOption(findRecipe.getPostOption());
		
		return recipeRepo.save(recipe);
	}
	
//	@PostMapping("/tambah/pengguna/{users}/kategori/{recipeCategory}")
//	public String addRecipe() {
//		return null;
//	}
}
