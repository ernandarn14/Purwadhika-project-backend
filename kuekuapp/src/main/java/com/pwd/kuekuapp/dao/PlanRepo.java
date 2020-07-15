package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwd.kuekuapp.entity.Plans;

public interface PlanRepo extends JpaRepository<Plans, Integer> {
	//public Plans findPlanName(String planName);
}
