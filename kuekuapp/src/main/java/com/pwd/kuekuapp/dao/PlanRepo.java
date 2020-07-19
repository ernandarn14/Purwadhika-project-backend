package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Plans;

public interface PlanRepo extends JpaRepository<Plans, Integer> {
	@Query(value= "SELECT * FROM plans order by plan_name asc", nativeQuery = true)
	public Iterable<Plans> getPlansAsc();
	
	@Query(value= "SELECT * FROM plans order by plan_name desc", nativeQuery = true)
	public Iterable<Plans> getPlansDesc();
	
	@Query(value= "SELECT * FROM plans where price >=?1 and price <= ?2 order by price asc", nativeQuery = true)
	public Iterable<Plans> getPlansByPriceAsc(double minPrice, double maxPrice);
	
	@Query(value= "SELECT * FROM plans where price >=?1 and price <= ?2 order by price desc", nativeQuery = true)
	public Iterable<Plans> getPlansByPriceDesc(double minPrice, double maxPrice);
	
	@Query(value= "SELECT * FROM plans where plan_period=?1 order by plan_name asc", nativeQuery = true)
	public Iterable<Plans> getPlansByPeriodAsc(String period);
	
	@Query(value= "SELECT * FROM plans where plan_period=?1 order by plan_name desc", nativeQuery = true)
	public Iterable<Plans> getPlansByPeriodDesc(String planPeriod);
	
	@Query(value= "SELECT * FROM plans where plan_period=?1 and price >=?2 and price <= ?3 order by price asc", nativeQuery = true)
	public Iterable<Plans> getPlansByPeriodPriceAsc(String planPeriod, double minPrice, double maxPrice);
	
	@Query(value= "SELECT * FROM plans where plan_period=?1 and price >=?2 and price <= ?3 order by price desc", nativeQuery = true)
	public Iterable<Plans> getPlansByPeriodPriceDesc(String planPeriod, double minPrice, double maxPrice);
}
