package com.pwd.kuekuapp.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.pwd.kuekuapp.entity.Tips;

public interface TipsService {
	public Iterable<Tips> getAllTips();
	public Optional<Tips> getTipsById(int id);
	public Iterable<Tips> getTipsByUser(int users);
//	public String addTips(MultipartFile file, String stringTips, int tipsId);
	public Tips updateTips(Tips tips, int userId);
	public void deleteTipsById(int id);
}
