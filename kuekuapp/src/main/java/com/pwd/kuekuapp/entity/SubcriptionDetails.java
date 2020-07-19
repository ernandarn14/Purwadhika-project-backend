package com.pwd.kuekuapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name="subscription_details")
@Immutable
public class SubcriptionDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "transaction_id")
	private int transactionId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "stardate")
	private Date stardate;
	
	@Column(name = "expiry")
	private Date expiry;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getStardate() {
		return stardate;
	}

	public void setStardate(Date stardate) {
		this.stardate = stardate;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	} 
	
	
}
