package com.pwd.kuekuapp.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Subscriptions;

public interface SubcriptionRepo extends JpaRepository<Subscriptions, Integer> {
	@Query(value = "SELECT s.stardate + INTERVAL COALESCE(SUM(CASE WHEN p.plan_period = 'tahun' THEN p.plan_duration END), 0) YEAR  + INTERVAL COALESCE(SUM(CASE WHEN p.plan_period = 'bulan' THEN p.plan_duration END), 0) MONTH + INTERVAL COALESCE(SUM(CASE WHEN p.plan_period = 'minggu' THEN p.plan_duration END), 0) WEEK + INTERVAL COALESCE(SUM(CASE WHEN p.plan_period = 'hari' THEN p.plan_duration END), 0) DAY FROM  subscriptions s JOIN transactions t ON t.id = s.transaction_id JOIN plans p ON p.id = t.plan_id WHERE t.id = ?1", nativeQuery = true)
	public Date getEndDate(int transactionId);
}
