package com.codingDojo.mvc.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingDojo.mvc.models.Idea;
import com.codingDojo.mvc.models.IdeaInfo;
import com.codingDojo.mvc.models.User;
import com.codingDojo.mvc.services.UserService;
import com.codingDojo.mvc.validators.UserValidator;

@Controller
public class EventController {
	
	private final UserService userService ;
	private final UserValidator userValidator ;
	
	
	public EventController(UserService userService, UserValidator userValidator) {
		this.userService =  userService ;
		this.userValidator = userValidator ;
	}
	
	@RequestMapping("/")
	public String index(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		return "welcome.jsp";
	}
	
	@RequestMapping("/login")
	public String getlogin(@Valid @ModelAttribute("user") User user, BindingResult result) {
		return "welcome.jsp";
	}
	
	@RequestMapping(value="/login" , method= RequestMethod.POST)
	public String logIn(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam("loginEmail") String email, 
			@RequestParam("loginPassword") String password,
			Model model ,HttpSession session) {
	
			User users = this.userService.findUserByEmail(email) ;
			boolean isUserAuthenticated = this.userService.authenticateUser(email, password) ;
			
			if(isUserAuthenticated) {
				 session.setAttribute("userId", users.getId());
				return "redirect:/ideas";
			}
			else {
				model.addAttribute("error", "invalid login!" ) ;
				return "welcome.jsp";
			}
		
	}
	
	@RequestMapping("/registration")
    public String getregisterUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
		return "welcome.jsp";
	}
    
	@RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,
    		Model model , RedirectAttributes redirectAtt,
    		HttpSession session) {
        userValidator.validate(user, result);
        if(result.hasErrors()) {
            return "welcome.jsp";
        }
        User u = userService.createUser(user);
        session.setAttribute("userId", u.getId());
        return "redirect:/ideas";
    }  
	
	@RequestMapping("/ideas")
	public String home(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
	
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		List<IdeaInfo> ideaInfo = this.userService.findAllIdeaInfos(currUser) ;
		System.out.print("IdeaInfo" + ideaInfo );
		model.addAttribute("ideas", ideaInfo);
		model.addAttribute("user", currUser);
		return "ideas.jsp";
	}
	
	@RequestMapping("/ideas/new")
	public String getNewIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result,
			@Valid @ModelAttribute("user") User user,
			Model model, HttpSession session) {
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		 if(currUser == null || result.hasErrors()) {
	            return "welcome.jsp";
		 }
		return "add.jsp";
	}
	
	@RequestMapping(value="/ideas/new",  method=RequestMethod.POST)
	public String addIdeas(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, Model model, HttpSession session) {
		
		if(result.hasErrors()) {
			return "add.jsp";
		}
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		idea.setCreator(currUser);
		idea.getLikedBy().add(currUser);
		
		this.userService.createIdea(idea);
		return "redirect:/ideas";
	}

	@RequestMapping("/ideas/{id}")
	public String detailsTask(@PathVariable("id") Long id, Model model,
			@Valid @ModelAttribute("user") User user, HttpSession session ) {
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		
		Idea idea = this.userService.findIdeaById(id);
		
		String creatorName = idea.getCreator().getName() ;
		List<User> likingPersons = idea.getLikedBy() ;
		
		model.addAttribute("idea", idea ) ;
		model.addAttribute("likingPersons", likingPersons ) ;
		//model.addAttribute("createdBy", creatorName ) ;
		return "details.jsp";
	}
	//edit and delete will goes here
	
	@RequestMapping("/ideas/{id}/edit")
	public String editTask(@Valid @ModelAttribute("idea") Idea idea, 
			BindingResult result, @Valid @ModelAttribute("user") User user,
			@PathVariable("id") Long id,
			Model model, HttpSession session ) {
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		
		Idea oldIdea = this.userService.findIdeaById(id);
		model.addAttribute("idea", oldIdea ) ;
		
		return "edit.jsp";
	}
	

	@RequestMapping(value="/ideas/{id}/edit", method=RequestMethod.POST)
	public String editSaveTask(
			@Valid @ModelAttribute("idea") Idea idea, BindingResult result,
			@PathVariable("id") Long id, Model model , HttpSession session) {
		
		if(result.hasErrors()) {
			return "edit.jsp";
		}
		
		Idea oldIdea = this.userService.findIdeaById(id);
		oldIdea.setContent(idea.getContent());
		
		this.userService.createIdea(oldIdea); ;
		return "redirect:/ideas";
		
	}
	// /ideas/${idea.id}/like
	
	@RequestMapping(value="/ideas/{id}/like")
	public String likeIdea(
			@Valid @ModelAttribute("idea") Idea idea,
			@Valid @ModelAttribute("user") User user,
			BindingResult result,
			@PathVariable("id") Long id, Model model , HttpSession session) {
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		Idea oldIdea = this.userService.findIdeaById(id);
		List<User> likers = oldIdea.getLikedBy() ;
		boolean isLiked = false ;
		for(User liker : likers) {
			if(liker.equals(currUser)) {
				model.addAttribute("isLiked", true ) ;
				break ;
			}
		}
		if(!isLiked) {
			
			oldIdea.getLikedBy().add(currUser);
			model.addAttribute("isLiked", true ) ;
		}
		
		this.userService.createIdea(oldIdea); 
		return "redirect:/ideas";
		
	}
	
	@RequestMapping(value="/ideas/{id}/unlike")
	public String unlikeIdea(
			@Valid @ModelAttribute("idea") Idea idea,
			@Valid @ModelAttribute("user") User user, BindingResult result,
			@PathVariable("id") Long id, Model model , HttpSession session) {
		//Dont allow user to be on this page if not logged in
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		
		Idea oldIdea = this.userService.findIdeaById(id);
		List<User> likers = oldIdea.getLikedBy() ;
		boolean isLiked = true ;
		for(User liker : likers) {
			if(liker.equals(currUser)) {
				model.addAttribute("isLiked", false ) ;
				oldIdea.getLikedBy().remove(currUser);
				break ;
			}
		}
		
		
		this.userService.createIdea(oldIdea); 
		return "redirect:/ideas";
		
	}
	// /ideas/{id}/delete
	
	@RequestMapping(value="/ideas/{id}/delete")
	public String deleteTask(
			@Valid @ModelAttribute("idea") Idea idea, BindingResult result,
			@Valid @ModelAttribute("user") User user,
			@PathVariable("id") Long id, Model model , HttpSession session) {
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		
		if(result.hasErrors()) {
			return "edit.jsp";
		}
		
		 this.userService.deleteByid(id);
		
		return "redirect:/ideas";
		
	}
	
	@RequestMapping("/sortUp")
	public String sortUp(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
	
		List<IdeaInfo> ideaInfo = this.userService.findAllIdeaInfosSortedUp(currUser) ;
		System.out.print("IdeaInfo" + ideaInfo );
		model.addAttribute("ideas", ideaInfo);
		model.addAttribute("user", currUser);
		return "ideas.jsp";
	}
	
	@RequestMapping("/sortDown")
	public String sortDown(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		
		User currUser = this.userService.findUserById((Long)session.getAttribute("userId")) ;
		if(currUser == null) {
			return "welcome.jsp";
		}
		List<IdeaInfo> ideaInfo = this.userService.findAllIdeaInfosSortedDown(currUser) ;
		System.out.print("IdeaInfo" + ideaInfo );
		model.addAttribute("ideas", ideaInfo);
		model.addAttribute("user", currUser);
		return "ideas.jsp";
	}
	
	/**
	 * Log out 
	 */
	
	@RequestMapping(value="/logout")
	public String logout(@Valid @ModelAttribute("user") User user, BindingResult result , HttpSession session) {
		session.removeAttribute("userId");
		return "welcome.jsp";
	}
	

}
