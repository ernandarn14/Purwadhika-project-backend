package com.pwd.kuekuapp.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwd.kuekuapp.dao.SubcriptionRepo;
import com.pwd.kuekuapp.dao.TransactionRepo;
import com.pwd.kuekuapp.entity.Subscriptions;
import com.pwd.kuekuapp.entity.Transactions;
import com.pwd.kuekuapp.service.SubcriptionService;

@Service
public class SubcriptionServiceImpl implements SubcriptionService {
	
	@Autowired
	private SubcriptionRepo subcriptionRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;

	@Override
	@Transactional
	public Iterable<Subscriptions> getAllSubscriptions() {
		return subcriptionRepo.findAll();
	}

	@Override
	@Transactional
	public Optional<Subscriptions> getSubscriptionsById(int id) {
		return subcriptionRepo.findById(id);
	}

	@Override
	@Transactional
	public Subscriptions addSubscriptions(Subscriptions subscriptions, int transactionId) {
		Transactions findTransaction = transactionRepo.findById(transactionId).get();
		
		subscriptions.setId(0);
		subscriptions.setTransactions(findTransaction);
		subscriptions.setStardate(findTransaction.getConfirmDate());
		Date durationEnd = subcriptionRepo.getEndDate(transactionId);
		subscriptions.setEndDate(durationEnd);
		return subcriptionRepo.save(subscriptions);
	}

	@Override
	@Transactional
	public void deleteSubscription(int id) {
		Subscriptions findSubscriptions = subcriptionRepo.findById(id).get();
		
		if(findSubscriptions == null)
			throw new RuntimeException("Data tidak ditemukan");
		
		findSubscriptions.getTransactions().setSubcriptions(null);
		findSubscriptions.setTransactions(null);
		
		subcriptionRepo.deleteById(id);
	}

}
