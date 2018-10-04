	package com.codingDojo.mvc.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingDojo.mvc.models.User;
import com.codingDojo.mvc.repository.UserRepo;
@Component
public class UserValidator implements Validator {
	
	private final UserRepo userRepo ;
	
	public UserValidator(UserRepo userRepo) {
		this.userRepo = userRepo ;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }  
		
		User existingUser = this.userRepo.findUserByEmail(user.getEmail());
	        
        if (existingUser != null && user.getEmail().equals(existingUser.getEmail())) {
            // 3
            errors.rejectValue("email", "Match");
        } 
        
        if (user.getPassword().length() < 8 ) {
            // 3
            errors.rejectValue("password", "Match");
        }  
	}

}
