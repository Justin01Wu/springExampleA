package test.spring;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import test.spring.beans.User;
import test.spring.exception.EntityNotFoundException;
import test.spring.service.UserService;

@Component  // traditional spring way to declare a bean
//@Qualifier("myUserService")  // spring way to narrow down range of bean
public class UserServiceImpl implements UserService {
	
	private Map<Integer, User> idUserMap = new HashMap<Integer, User>();
	
	
	public UserServiceImpl(){
		User user = new User();
		user.setId(99);
		user.setFirstName("Justin");
		user.setLastName("Wu");
		idUserMap.put(user.getId(), user);
	}
	
	@PostConstruct
	private void testPostConstruct(){
		System.out.println("             ==> testPostConstruct in PersonServiceImpl...");		
	}	
	
	public synchronized void saveUser(User user){
		idUserMap.put(user.getId(), user);
	}
	
	public synchronized void deleteUser(int id){
		
		User user = idUserMap.get(id);
		if(user ==  null){
			throw new EntityNotFoundException("user name exists: " + id);
		}
		idUserMap.remove(id); 
	}
	
	public synchronized User getUser(int id){
		return idUserMap.get(id);
	}
	
	public Map<Integer, User> getAll(){
		return idUserMap;
	}

}
