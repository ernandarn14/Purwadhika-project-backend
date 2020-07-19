package com.pwd.kuekuapp.service;

import java.util.Optional;
import com.pwd.kuekuapp.entity.Tips;

public interface TipsService {
	public Iterable<Tips> getAllTips();
	public Optional<Tips> getTipsById(int id);
	public Iterable<Tips> getTipsByUser(int users, String sort);
	public void deleteTipsById(int id);
	public Iterable<Tips> adminGetAllTips(String sort);
}
