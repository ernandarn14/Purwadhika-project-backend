package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pwd.kuekuapp.dao.TipsRepo;
import com.pwd.kuekuapp.entity.Tips;
import com.pwd.kuekuapp.service.TipsService;

@Service
public class TipsServiceImpl implements TipsService {
	@Autowired
	private TipsRepo tipsRepo;

	@Override
	@Transactional
	public Iterable<Tips> getAllTips() {
		return tipsRepo.findAll();
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

	@Override
	@Transactional
	public Iterable<Tips> adminGetAllTips(String sort) {
		if(sort.equals("asc")) {
			return tipsRepo.orderTipsNameAsc();
		} else {
			return tipsRepo.orderTipsNameDesc();
		}
	}


}
