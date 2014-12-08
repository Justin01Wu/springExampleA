package test.spring;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import test.spring.beans.Person;
import test.spring.service.MyWebService;
import test.spring.service.PersonService;

@Component
@WebService(endpointInterface = "test.spring.service.MyWebService", serviceName="myWebService")
public class PersonServiceEndPoint implements MyWebService { // extends SpringBeanAutowiringSupport {
	
	private static int id = 700;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Inject
	private PersonService personService;  
	
	@WebMethod
	public void addPerson(Person person){
		id ++;
		person.setId(id);
		if(log.isDebugEnabled()){
			log.debug("going to save a new person: " + person.getId() + ", " + person.getName());
		}
		personService.savePerson(person);
	}
	
	@WebMethod
	public Person getDefaultPerson(){

		if(log.isDebugEnabled()){
			log.debug("going to get default person " );
		}
		return personService.getPerson(99);
	}
	
	@WebMethod
	public void deletePerson(int id){
		if(log.isDebugEnabled()){
			log.debug("going to delete a person: " + id );
		}
		personService.deletePerson(id);
		
	}

}
