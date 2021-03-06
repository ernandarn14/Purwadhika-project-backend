package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.Transactions;

public interface TransactionService {
	public Iterable<Transactions> getAllTransactions();
	public Iterable<Transactions> getAllStatusTransactions(String status);
	public Optional<Transactions> getTransactionById(int id);
	public Transactions addTransactions(Transactions transactions, int planId, int userId);
	public Iterable<Transactions> getByStatus(String status, int userId);
	public Iterable<Transactions> getByPendingStatus(String status, int userId);
	public Transactions confirmPayment(int id);
	public Transactions rejectPayment(Transactions transactions, int id, String failedNote);
	public Iterable<Transactions> getBestSellerPlans(String sort);
	public Iterable<Transactions> getBestSellerPeriodPlans(String planPeriod, String sort);
	public void deleteTransaction(int id);
}
