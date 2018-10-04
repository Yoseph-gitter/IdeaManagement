package com.codingDojo.mvc.models;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="newtasks")
public class NewTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	private String name ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="assigner_id")
	private User assiginee ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User creator ;
	
	private String priority ;
	
	public NewTask() {
		
	}
	public NewTask(String desc, User assiginee, String priority) {
		super();
		this.name = desc;
		this.assiginee = assiginee;
		this.priority = priority;
	}


	@Column(updatable=false)
	private Date createdAt ;
	private Date updatedAt ;
	
	
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date() ;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String desc) {
		this.name = desc;
	}

	public User getAssiginee() {
		return assiginee;
	}

	public void setAssiginee(User assiginee) {
		this.assiginee = assiginee;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getId() {
		return id;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date() ;
	}

}