package com.pwd.kuekuapp.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pwd.kuekuapp.entity.Tips;

public interface TipsRepo extends JpaRepository<Tips, Integer>, PagingAndSortingRepository<Tips, Integer> {
	public Optional<Tips> findByTipsName(String tipsName);
	
	@Query(value = "SELECT * FROM Tips WHERE user_id = ?1", nativeQuery = true)
	public Iterable<Tips> getTipsByUsers(int users);
	
	@Query(value = "SELECT * FROM Tips order by tips_name asc", nativeQuery = true)
	public Page<Tips> orderTipsNameAsc(Pageable pageable);
	
	@Query(value = "SELECT * FROM Tips order by tips_name desc", nativeQuery = true)
	public Page<Tips> orderTipsNameDesc(Pageable pageable);
	
	//admin filter
	@Query(value = "SELECT * FROM Tips order by tips_name asc", nativeQuery = true)
	public Iterable<Tips> orderTipsNameAsc();
	
	@Query(value = "SELECT * FROM Tips order by tips_name desc", nativeQuery = true)
	public Iterable<Tips> orderTipsNameDesc();
}
