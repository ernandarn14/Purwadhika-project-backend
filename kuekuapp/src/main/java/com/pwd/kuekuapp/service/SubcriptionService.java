package com.pwd.kuekuapp.service;

import java.util.Optional;

import com.pwd.kuekuapp.entity.Subscriptions;

public interface SubcriptionService {
	public Iterable<Subscriptions> getAllSubscriptions();
	public Optional<Subscriptions> getSubscriptionsById(int id);
	public Subscriptions addSubscriptions(Subscriptions subscriptions, int transactionId);
	public void deleteSubscription(int id);
}
