package com.pwd.kuekuapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwd.kuekuapp.dao.TipsRepo;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.Tips;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.TipsService;


@RestController
@RequestMapping("/tips")
@CrossOrigin
public class TipsController {
	@Autowired 
	private TipsService tipsService;
	
	@Autowired
	private TipsRepo tipsRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@GetMapping
	public Iterable<Tips> getAllTips(){
		return tipsService.getAllTips();
	}
	
	@GetMapping("/{id}")
	public Optional<Tips> getTipsById(@PathVariable int id){
		return tipsService.getTipsById(id);
	}
	
	@GetMapping("/pengguna/{users}")
	public Iterable<Tips> getTipsByUser(@PathVariable int users){
		return tipsService.getTipsByUser(users);
	}
	
	@PostMapping("/pengguna/{users}")
	public Tips addTips(@RequestParam("file") MultipartFile file, @RequestParam("userData") String stringTips,  @PathVariable int users) throws JsonMappingException, JsonProcessingException{
		//return tipsService.addTips(file, stringTips, tipsId);
		Date date = new Date();

		Tips tips = new ObjectMapper().readValue(stringTips, Tips.class);
		String fileExtension = file.getContentType().split("/")[1];
		String newFileName = "TIPS-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);

		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tips/download/")
				.path(fileName).toUriString();

		tips.setTipsImage(fileDownloadUri);
		tips.setId(0);
		tips.setPostedDate(date);
		
		Users findUser = userRepo.findById(users).get();
		tips.setUsers(findUser);

		return tipsRepo.save(tips);
	}
	
	@GetMapping("/download/{fileName:.+}") //.+ extension .jpg/png
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
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
	
//	@PostMapping("/tambah/pengguna/{userId}")
//	public Tips addNewTips(@RequestBody Tips tips, @PathVariable int userId) {
//		Users findUserId = userRepo.findById(userId).get();
//
//		if (findUserId == null)
//			throw new RuntimeException("Pengguna tidak ditemukan");
//		tips.setUsers(findUserId);
//		return tipsRepo.save(tips);
//	}
	
	@PutMapping("/edit/{tipsId}/pengguna/{userId}")
	public String updateTips(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("userData") String stringTips,  @PathVariable int userId, @PathVariable int tipsId) throws JsonMappingException, JsonProcessingException {
		//return tipsService.updateTips(tips, userId);
		
		Tips findTips = tipsRepo.findById(tipsId).get();
		
		Date date = new Date();

		findTips = new ObjectMapper().readValue(stringTips, Tips.class);
		String fileDownloadUri = findTips.getTipsImage();
		
		if(file.toString() != "Optional.empty") {
			String fileExtension = file.get().getContentType().split("/")[1];
			String newFileName = "TIPS-" + date.getTime() + "." + fileExtension;

			String fileName = StringUtils.cleanPath(newFileName);

			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
			
			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			 fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tips/download/")
					.path(fileName).toUriString();
		}

		findTips.setTipsImage(fileDownloadUri);
		
		Users findUser = userRepo.findById(userId).get();
		findTips.setUsers(findUser);
		findTips.setEditDate(date);
		
		tipsRepo.save(findTips);

		return fileDownloadUri;
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteTipsById(@PathVariable int id) {
		tipsService.deleteTipsById(id);
	}
	
	@GetMapping("/admin/{sort}")
	public Iterable<Tips> adminGetAllTips(@PathVariable String sort){
		return tipsService.adminGetAllTips(sort);
	}
	
	
	@GetMapping("/sort/{sort}")
	public Page<Tips> findTipsByName(@PathVariable String sort, Pageable pageable){
		if (sort.equals("asc")) {
			return tipsRepo.orderTipsNameAsc(pageable);
		} else {
			return tipsRepo.orderTipsNameDesc(pageable);
		}
	}
}
