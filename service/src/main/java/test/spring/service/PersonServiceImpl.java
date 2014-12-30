package test.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import test.spring.beans.Person;
import test.spring.exception.EntityNotFoundException;
import test.spring.exception.UserNameExistsException;

//@Component  // traditional spring way to declare a bean
//@Qualifier("default34")  // spring way to narrow down range of bean

@Named("default23")   // spring also support new JSR330 way , it equals to @Component + @Qualifier

public class PersonServiceImpl implements PersonService {
	
	private static int id = 200;	
	private Map<Integer, Person> idPersonMap = new HashMap<Integer, Person>();
	
	private Set<String> allNames = new HashSet<String>();
	
	public PersonServiceImpl(){
		Person person = new Person(99, "samplePerson", "abcD1234");
		person.setBirth(new Date());
		person.addEmails("wuyg719@gmail.com");
		person.addEmails("justin1.wu@gmail.com");
		idPersonMap.put(person.getId(), person);
		allNames.add(person.getName());
	}
	
	@PostConstruct
	private void testPostConstruct(){
		System.out.println("             ==> testPostConstruct in PersonServiceImpl...");		
	}	
	
	@Override
	public synchronized void savePerson(Person person){		

		if(allNames.contains(person.getName())){
			throw new UserNameExistsException("user name exists: " + person.getName(), "name");
		}
		id ++;
		person.setId(id);
		
		idPersonMap.put(person.getId(), person);
		allNames.add(person.getName());
	}
	
	@Override
	public synchronized void updatePerson(Person person){
		Person oldPerson = getPerson(person.getId());
		if(oldPerson == null){
			throw new RuntimeException("old person is not found");
		}
		idPersonMap.remove(oldPerson);
		allNames.remove(oldPerson.getName());
		
		idPersonMap.put(person.getId(), person);
		allNames.add(person.getName());
	}
	
	@Override
	public synchronized void deletePerson(int id){
		
		Person person = idPersonMap.get(id);
		if(person ==  null){
			throw new EntityNotFoundException("user name exists: " + id);
		}
		idPersonMap.remove(id); 
		allNames.remove(person.getName());
	}
	
	@Override
	public synchronized Person getPerson(int id){
		return idPersonMap.get(id);
	}
	
	@Override
	public Map<Integer, Person> getAll(){
		return idPersonMap;
	}

}
