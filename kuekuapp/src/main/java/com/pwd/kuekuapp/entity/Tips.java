package com.pwd.kuekuapp.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Tips {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String tipsImage;
	
	private String tipsName;
	
	private Date postedDate;
	
	private Date editDate;
	
	private String postOption;
	
	public String getPostOption() {
		return postOption;
	}

	public void setPostOption(String postOption) {
		this.postOption = postOption;
	}

	@Lob 
	@Column(length=1512)
	private String tipsContent;
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private Users users;
	
	public String getTipsImage() {
		return tipsImage;
	}

	public void setTipsImage(String tipsImage) {
		this.tipsImage = tipsImage;
	}

//	public Tips() {
//		postedDate = new Date();
//	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipsName() {
		return tipsName;
	}

	public void setTipsName(String tipsName) {
		this.tipsName = tipsName;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getTipsContent() {
		return tipsContent;
	}

	public void setTipsContent(String tipsContent) {
		this.tipsContent = tipsContent;
	}

}
