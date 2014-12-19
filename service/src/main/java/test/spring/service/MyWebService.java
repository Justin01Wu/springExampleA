package test.spring.service;

import javax.jws.WebService;

import test.spring.beans.Person;

@WebService
public interface MyWebService {
	
	public void addPerson(Person person);
	public void deletePerson(int id);

	public Person getDefaultPerson();

}
