package com.codingDojo.mvc.models;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name="usersdojo")
public class User {
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Idea> getCreated() {
		return created;
	}
	public void setCreated(List<Idea> created) {
		this.created = created;
	}
	public List<Idea> getLikes() {
		return likes;
	}
	public void setLikes(List<Idea> likes) {
		this.likes = likes;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@Size(min=3, message="Name should be more than 3 chars")
	private String name ;
    
	@Email(message="Email must be valid")
	@Size(min=5, message="Email should be more than 5 chars")
	private String email;
	
	@Size(min=8, message="Password must be greater than 8 characters")
	private String password ;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="creator")
	private List<Idea> created ;
	

	@ManyToMany(cascade=CascadeType.ALL,  fetch = FetchType.LAZY)
	@JoinTable(
			name= "users_ideas",
			joinColumns = @JoinColumn (name="liker_id"),
			inverseJoinColumns=@JoinColumn(name = "ideas_id"))
	private List<Idea> likes ;
	
	public User() {
		
	}
	public User(String name, String email, String password,
			String passwordConfirmation) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Long getId() {
		return id;
	}
    
	@Transient
	private String passwordConfirmation ;
	
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