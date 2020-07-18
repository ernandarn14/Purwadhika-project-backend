package com.pwd.kuekuapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pwd.kuekuapp.entity.Plans;
import com.pwd.kuekuapp.entity.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1 and user_id = ?2", nativeQuery = true)
	public Iterable<Transactions> findByStatus(String status, int userId);
	
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1 and user_id = ?2 and payment_reciept is null", nativeQuery = true)
	public Iterable<Transactions> findByPendingStatus(String status, int userId);
	
	@Query(value = "SELECT * FROM Transactions WHERE status = ?1", nativeQuery = true)
	public Iterable<Transactions> findAllByStatus(String status);
	
	
	//query report
	@Query(value="SELECT count(*) as total, t.* from transactions t join plans p on p.id = t.plan_id where t.status = 'sukses' group by t.plan_id order by total asc", nativeQuery = true)
	public Iterable<Transactions> getBestSellerPlansAsc();
	
	@Query(value="SELECT count(*) as total, t.* from transactions t join plans p on p.id = t.plan_id where t.status = 'sukses' group by t.plan_id order by total desc", nativeQuery = true)
	public Iterable<Transactions> getBestSellerPlansDesc();
	
	@Query(value="select count(*) as total, t.* from transactions t join plans p on p.id = t.plan_id where status = 'sukses' and plan_period=?1 order by total asc", nativeQuery = true)
	public Iterable<Transactions> getBestPlansByPeriodAsc(String planPeriod);
	
	@Query(value="select count(*) as total, t.* from transactions t join plans p on p.id = t.plan_id where status = 'sukses' and plan_period=?1 order by total asc", nativeQuery = true)
	public Iterable<Transactions> getBestPlansByPeriodDesc(String planPeriod);
}
