package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1 and user_id = ?2", nativeQuery = true)
	public Iterable<Transactions> findByStatus(String status, int userId);
	
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1 and user_id = ?2 and payment_reciept is null", nativeQuery = true)
	public Iterable<Transactions> findByPendingStatus(String status, int userId);
	
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1", nativeQuery = true)
	public Iterable<Transactions> findAllByStatus(String status);
}
