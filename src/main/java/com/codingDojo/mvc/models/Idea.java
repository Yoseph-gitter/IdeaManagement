package com.codingDojo.mvc.models;

import java.util.*;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;



@Entity
@Table(name="ideas")
public class Idea {
	
	public Idea() {
		if(this.likedBy == null) {
			this.likedBy = new ArrayList();
		}	
	}
	public Idea(String content) {
		super();
		this.content = content;
		if(this.likedBy == null) {
			this.likedBy = new ArrayList();
		}	
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@Size(min=6, message="Idea should be more than 6 chars")
	private String content ;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<User> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(List<User> likedBy) {
		this.likedBy = likedBy;
	}

	public Long getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User creator ;
	
	@ManyToMany(cascade=CascadeType.ALL,  fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_ideas",
			joinColumns = @JoinColumn (name="ideas_id"),
			inverseJoinColumns=@JoinColumn(name = "liker_id"))
	private List<User> likedBy ;
	

	
	@Column(updatable=false)
	private Date createdAt ;
	private Date updatedAt ;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date() ;
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date() ;
	}
	
}
