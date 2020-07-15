package com.pwd.kuekuapp.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

@Service
public class TipsServiceImpl implements TipsService {
	@Autowired
	private TipsRepo tipsRepo;

	@Autowired
	private UserRepo userRepo;

	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";

	@Override
	@Transactional
	public Iterable<Tips> getAllTips() {
		return tipsRepo.findAll();
	}

//	@Override
//	@Transactional
//	public Tips addTips(Tips tips) {
	// Users findUserId = userRepo.findById(userId).get();

//		if (findUserId == null)
//			throw new RuntimeException("Pengguna tidak ditemukan");

//		tips.setId(0);
	// tips.setUsers(findUserId);
//		return tipsRepo.save(tips);
//	}

	@Override
	@Transactional
	public Tips updateTips(Tips tips, int userId) {
		Optional<Tips> findTips = tipsRepo.findById(tips.getId());

		if (findTips.get() == null)
			throw new RuntimeException("Artikel tidak ditemukan");

		Users findUserId = userRepo.findById(userId).get();

		if (findUserId == null)
			throw new RuntimeException("Pengguna tidak ditemukan");
		tips.setUsers(findUserId);
		return tipsRepo.save(tips);
	}

	@Override
	@Transactional
	public void deleteTipsById(int id) {
		Tips findTips = tipsRepo.findById(id).get();

		if (findTips == null) {
			throw new RuntimeException("Artikel tidak ditemukan");
		}
		
		findTips.getUsers().setTips(null);
		
		findTips.setUsers(null);

		tipsRepo.deleteById(id);
	}
	
//	@Override
//	public void deleteTipsById(Tips tips) {
//		tips.getUsers().setTips(null);
//		tips.setUsers(null);
//		tipsRepo.delete(tips);
//		
//	}

	@Override
	@Transactional
	public Optional<Tips> getTipsById(int id) {
		return tipsRepo.findById(id);
	}

	@Override
	@Transactional
	public Iterable<Tips> getTipsByUser(int users) {
		return tipsRepo.getTipsByUsers(users);
	}

//	@Override
//	@Transactional
//	public String addTips(MultipartFile file, String stringTips, int tipsId){
//		Date date = new Date();
//
//		Tips tipsFile; 
////		= new ObjectMapper().readValue(stringTips, Tips.class);
//		String fileExtension = file.getContentType().split("/")[1];
//		String newFileName = "TIPS-" + date.getTime() + "." + fileExtension;
//
//		String fileName = StringUtils.cleanPath(newFileName);
//
//		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
//
//		try {
//			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tips/download/")
//				.path(fileName).toUriString();
//
//		tipsFile.setTipsImage(fileDownloadUri);
//		tipsFile.setId(0);
//		
//		tipsRepo.save(tipsFile); 
//		
//		return fileDownloadUri;
//	}

}
