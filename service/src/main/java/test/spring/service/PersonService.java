package test.spring.service;

import java.util.Map;

import test.spring.beans.Person;

public interface PersonService {
	
	public void savePerson(Person person);
	public void updatePerson(Person person);
	public void deletePerson(int id);	
	public Person getPerson(int id);
	public Map<Integer, Person> getAll();
	

}
