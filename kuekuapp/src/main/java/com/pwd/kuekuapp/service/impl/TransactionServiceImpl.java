package com.pwd.kuekuapp.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.PlanRepo;
import com.pwd.kuekuapp.dao.TransactionRepo;
import com.pwd.kuekuapp.dao.UserRepo;
import com.pwd.kuekuapp.entity.Plans;
import com.pwd.kuekuapp.entity.Transactions;
import com.pwd.kuekuapp.entity.Users;
import com.pwd.kuekuapp.service.TransactionService;
import com.pwd.kuekuapp.util.EmailUtil;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PlanRepo planRepo;
	
	@Autowired
	private EmailUtil emailUtil;

	@Override
	@Transactional
	public Iterable<Transactions> getAllTransactions() {
		return transactionRepo.findAll();
	}

	@Override
	@Transactional
	public Transactions addTransactions(Transactions transactions, int planId, int userId) {
		Date date = new Date();
		Users findUser = userRepo.findById(userId).get();

		if (findUser == null)
			throw new RuntimeException("Pengguna Tidak Ditemukan");

		Plans findPlan = planRepo.findById(planId).get();
		if (findPlan == null) {
			throw new RuntimeException("Paket Langganan Tidak Ditemukan");
		}

		transactions.setId(0);
		transactions.setUser(findUser);
		transactions.setPlans(findPlan);
		transactions.setCheckoutDate(date);
		transactions.setStatus("pending");
		transactions.setTotalPayment(findPlan.getPrice());

		return transactionRepo.save(transactions);
	}

	@Override
	@Transactional
	public Optional<Transactions> getTransactionById(int id) {
		return transactionRepo.findById(id);
	}

	@Override
	@Transactional
	public Iterable<Transactions> getByStatus(String status, int userId) {
		Users findUser = userRepo.findById(userId).get();
		if (findUser == null)
			throw new RuntimeException("Pengguna Tidak Ditemukan");
		return transactionRepo.findByStatus(status, userId);
	}

	@Override
	@Transactional
	public Iterable<Transactions> getByPendingStatus(String status, int userId) {
		Users findUser = userRepo.findById(userId).get();
		if (findUser == null)
			throw new RuntimeException("Pengguna Tidak Ditemukan");
		return transactionRepo.findByPendingStatus(status, userId);
	}

	@Override
	@Transactional
	public Iterable<Transactions> getAllStatusTransactions(String status) {
		return transactionRepo.findAllByStatus(status);
	}

	@Override
	@Transactional
	public Transactions confirmPayment(int id) {
		Date date = new Date();

		Transactions findTransaction = transactionRepo.findById(id).get();
		if (findTransaction == null)
			throw new RuntimeException("Transaksi Tidak Ditemukan");

		findTransaction.setConfirmDate(date);
		findTransaction.setStatus("sukses");
		findTransaction.getUser().setMembership("premium");
		Transactions newData = transactionRepo.save(findTransaction);
		
		String bodyMessage = "<h2>Hai, " + findTransaction.getUser().getUsername() + "</h2>\n";
		bodyMessage += "<p>Pembayaran kamu telah di konfirmasi, berikut detail transaksi kamu: </p>\n";
		bodyMessage += "<p>Nomor Transaksi: " + findTransaction.getId() + "</p>\n";
		bodyMessage += "<p>Nama Langganan: " + findTransaction.getPlans().getPlanName() + "</p>\n";
		bodyMessage += "<p>Total Pembayaran: " + findTransaction.getTotalPayment() + "</p>\n";
		bodyMessage += "<p>Metode Pembayaran: " + findTransaction.getPaymentMethod() + "</p>\n";
		bodyMessage += "\n<p>Terima kasih telah berlangganan!</p>\n";
		bodyMessage += "\n<p>Kueku Team</p>\n";

		emailUtil.sendEmail(findTransaction.getUser().getEmail(), "Konfirmasi Pembayaran", bodyMessage);
		return newData;
	}

	@Override
	@Transactional
	public Transactions rejectPayment(Transactions transactions, int id) {
		Date date = new Date();

		Transactions findTransaction = transactionRepo.findById(id).get();
		if (findTransaction == null)
			throw new RuntimeException("Transaksi Tidak Ditemukan");

		findTransaction.setConfirmDate(date);
		findTransaction.setStatus("gagal");
		findTransaction.setFailedNote(null);
		Transactions newData = transactionRepo.save(findTransaction);
		return newData;
	}

}
