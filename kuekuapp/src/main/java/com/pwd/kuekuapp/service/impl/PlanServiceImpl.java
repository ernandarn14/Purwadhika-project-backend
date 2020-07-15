package com.pwd.kuekuapp.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.PlanRepo;
import com.pwd.kuekuapp.entity.Plans;
import com.pwd.kuekuapp.service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	private PlanRepo planRepo;

	@Override
	@Transactional
	public Iterable<Plans> getAllPlans() {
		return planRepo.findAll();
	}

	@Override
	@Transactional
	public Plans addNewPlans(Plans plan) {
		plan.setId(0);
		return planRepo.save(plan);
	}

	@Override
	@Transactional
	public Optional<Plans> getPlansById(int id) {
		return planRepo.findById(id);
	}

	@Override
	@Transactional
	public Plans editPlans(Plans plan) {
		Plans findPlan = planRepo.findById(plan.getId()).get();
		if (findPlan == null){
			throw new RuntimeException("Langganan tidak ditemukan");
		}
		
		return planRepo.save(plan);
	}

	@Override
	@Transactional
	public void deletePlans(int id) {
		Plans findPlan = planRepo.findById(id).get();
		if (findPlan == null){
			throw new RuntimeException("Langganan tidak ditemukan");
		}
		
		findPlan.getTransactions().forEach(plan -> {
			plan.setPlans(null);
			//planRepo.save(plan);
		});
		findPlan.setTransactions(null);
		
		planRepo.deleteById(id);
		
	}

}
