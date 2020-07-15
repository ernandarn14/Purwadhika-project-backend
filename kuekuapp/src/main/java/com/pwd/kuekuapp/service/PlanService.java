package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.Plans;

public interface PlanService {
	public Iterable<Plans> getAllPlans();
	public Optional<Plans> getPlansById(int id);
	public Plans addNewPlans(Plans plan);
	public Plans editPlans(Plans plan);
	public void deletePlans(int id);
}
