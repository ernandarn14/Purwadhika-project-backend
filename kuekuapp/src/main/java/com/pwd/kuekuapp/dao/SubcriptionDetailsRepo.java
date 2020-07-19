package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.SubcriptionDetails;

public interface SubcriptionDetailsRepo extends JpaRepository<SubcriptionDetails, Integer> {
	@Query(value="SELECT * FROM subscription_details WHERE username=?1", nativeQuery=true)
	public Iterable<SubcriptionDetails> getUniqueUsername(String username);
}
