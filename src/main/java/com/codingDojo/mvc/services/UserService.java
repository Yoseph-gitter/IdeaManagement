package com.codingDojo.mvc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.codingDojo.mvc.models.Idea;
import com.codingDojo.mvc.models.IdeaInfo;
import com.codingDojo.mvc.models.User;
import com.codingDojo.mvc.repository.IdeaRepo;
import com.codingDojo.mvc.repository.UserRepo;

@Service
public class UserService {
	private final UserRepo userRepo ;
	private final IdeaRepo ideaRepo ;
	
	
	public UserService(UserRepo userRepo, IdeaRepo ideaRepo) {
		this.userRepo = userRepo ;
		this.ideaRepo = ideaRepo ;
	}
	
	public User findUserByEmail(String email) {
		return this.userRepo.findUserByEmail(email) ;
	}
	
	public User createUser(User user) {
		String stronger_salt = BCrypt.gensalt(12) ;
		String hashedPassword = BCrypt.hashpw(user.getPassword(), stronger_salt) ;
		user.setPassword(hashedPassword);
		return this.userRepo.save(user);
	}
	
	public User findUserById(Long id) {
		return this.userRepo.findUserById(id) ;
	}
	
	// authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findUserByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
	        return matcher.find();
	}
//    /**
//     * Task related Service functionality
//     */
//    public NewTask saveTask(NewTask task) {
//    	return this.taskRepo.save(task) ;
//    }
//    public NewTask findTaskByIdNormal(Long id) {
//    	Optional<NewTask> newtask = this.taskRepo.findById(id);
//    	NewTask task = newtask.isPresent() ? newtask.get() : null ;
//    	return task ;
//    }
//    
//    public TaskInfo findTaskById(Long id) {
//    	Optional<NewTask> newtask = this.taskRepo.findById(id);
//    	NewTask task = newtask.isPresent() ? newtask.get() : null ;
//    	
//    	TaskInfo taskinf = new TaskInfo();
//    	
//		taskinf.setId(task.getId());
//		taskinf.setTask(task.getName());
//		taskinf.setCreator( this.findUserNameById(task.getCreator().getId()));
//		taskinf.setAssignee( this.findUserNameById(task.getAssiginee().getId()));
//		//taskinf.setAssignee(task.getAssiginee());
//		taskinf.setPriority(task.getPriority());
//    	
//    	return taskinf;
//    }
//    public List<TaskInfo> findAllTasks(){
//    	List<NewTask> tasks = this.taskRepo.findAll() ;
//    	System.out.println("Tasks 1" + tasks);
//    	List<TaskInfo> taskInfo = new ArrayList();
//    	for(NewTask task : tasks) {
//    		TaskInfo taskinf = new TaskInfo();
//    		taskinf.setId(task.getId());
//    		taskinf.setTask(task.getName());
//    		taskinf.setCreator( this.findUserNameById(task.getCreator().getId()));
//    		taskinf.setAssignee( this.findUserNameById(task.getAssiginee().getId()));
//    		//taskinf.setAssignee(task.getAssiginee());
//    		taskinf.setPriority(task.getPriority());
//    		taskInfo.add(taskinf) ;
//    		System.out.println("Tasks 2" + taskInfo);
//    	}
//    	return taskInfo;
//    }
//    
//    public String findUserNameById(Long creatAssign_id) {
//    	return this.userRepo.findUserNameById(creatAssign_id);
//    }
//   
  

	public List<User> findAllUsers() {
		
		return this.userRepo.findAll();
	}

	public List<Idea> findAllIdeas() {
		
		return this.ideaRepo.findAll();
	}
	
	public List<IdeaInfo> findAllIdeaInfos(User user){
		return this.turnIdeaToIdeasInfo(this.findAllIdeas(), user);
	}
	
	private List<IdeaInfo> turnIdeaToIdeasInfo(List<Idea> ideas, User user){
		List<IdeaInfo> ideaInfos = new ArrayList();
		for(Idea idea : ideas) {
			IdeaInfo ideaInfo = new IdeaInfo();
			ideaInfo.setIdea(idea);
			if(isUserLikesIdea(idea , user)) {
				ideaInfo.setLiked(true);
			}else {
				ideaInfo.setLiked(false);
			}
			ideaInfos.add(ideaInfo) ;
		}
		return ideaInfos ;
	}
	
	private boolean isUserLikesIdea(Idea idea , User user) {
		for(User liker : idea.getLikedBy()) {
			if(liker.equals(user)) {
				return true;
			}
		}
		return false ;
	}

	public void createIdea(@Valid Idea idea) {
		this.ideaRepo.save(idea) ;
	}

	public Idea findIdeaById(Long id) {
		
		Optional<Idea> opt = this.ideaRepo.findById(id);
		return opt.isPresent() ? opt.get() : null ;
	}

	public void deleteByid(Long id) {
		
		 this.ideaRepo.deleteById(id);;
	}

	public List<IdeaInfo> findAllIdeaInfosSortedDown(User currUser) {
		List<Idea> ideas = this.ideaRepo.findAllIdeasAscending();
		return turnIdeaToIdeasInfo(ideas, currUser);
	}

	public List<IdeaInfo> findAllIdeaInfosSortedUp(User currUser) {
		List<Idea> ideas = this.ideaRepo.findAllIdeasDescending() ;
		return turnIdeaToIdeasInfo(ideas, currUser);
	}


}
