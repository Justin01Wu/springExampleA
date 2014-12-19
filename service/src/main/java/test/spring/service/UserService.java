package test.spring.service;

import java.util.Map;

import test.spring.beans.Person;
import test.spring.beans.User;

public interface UserService {
	public void saveUser(User user);	
	public void deleteUser(int id);	
	public User getUser(int id);
	public Map<Integer, User> getAll();
}
