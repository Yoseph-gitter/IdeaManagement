package com.codingDojo.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingDojo.mvc.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	List<User> findAll();
	
	User findUserByEmail(String email);
	
	User findUserById(Long id) ;
	
	@Query(value="SELECT first_name FROM usersdojo user "
			+ "WHERE user.id = ?1" , nativeQuery=true )
	String findUserNameById(Long id) ;
	
}
