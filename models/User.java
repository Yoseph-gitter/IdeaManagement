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
	public List<NewTask> getCreated() {
		return created;
	}
	public void setCreated(List<NewTask> created) {
		this.created = created;
	}
	public List<NewTask> getAssigned() {
		return assigned;
	}
	public void setAssigned(List<NewTask> assigned) {
		this.assigned = assigned;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	@Size(min=3, message="First Name should be more than 3 chars")
	private String firstName ;
    
	@Email(message="Email must be valid")
	private String email;
	
	@Size(min=8, message="Password must be greater than 8 characters")
	private String password ;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="creator")
	private List<NewTask> created ;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="assiginee")
	private List<NewTask> assigned ;
	
	public User() {
		
	}
	public User(String firstName, String email, String password,
			String passwordConfirmation) {
		super();
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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