package com.pwd.kuekuapp.dao;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.RefMeasurements;

public interface RefMeasurementRepo extends JpaRepository<RefMeasurements, Integer> {
//	public Optional<RefMeasurements> findByMeasurementName(String measurementName);
	
	@Query(value = "SELECT LOWER(measurementName) FROM RefMeasurements WHERE measurementName = ?1", nativeQuery = true)
	public Iterable<RefMeasurements> findByMeasurementName(String measurementName);
}
