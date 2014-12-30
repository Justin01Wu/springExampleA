package test.spring;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import test.spring.beans.Person;
import test.spring.dto.PersonDTO;
import test.spring.exception.EntityNotFoundException;
import test.spring.exception.MethodNotFoundException;
import test.spring.exception.PersonCantDeleteException;
import test.spring.service.PersonService;
import test.spring.validator.ErrorInfo;

@Controller
@RequestMapping("/persons")
public class PersonApi {
	
	private static int id = 200;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	// traditional way to get a bean:
//	private PersonService personService = new PersonServiceImpl();   
	
// Spring standard way to get a bean	
//	@Autowired @Qualifier("default34")   // @Autowired default use type to find target bean
//	private PersonService personService;  // @Qualifier can't work with @Named
	
	@Inject @Named("default23")
	private PersonService personService;  
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody PersonDTO createPerson(@Valid @RequestBody PersonDTO person){

		if(log.isDebugEnabled()){
			log.debug("going to save a new person: " + person.getName());
		}
		personService.savePerson(person);
		return person;
	}
	
	// support both json and xml, set accetp: application/json for Json format, 
	// the default is json if accept is blank
	@RequestMapping(value = "/{id}", 
	method = RequestMethod.GET,
	headers ={"Accept=application/json,application/xml"},
	produces={"application/json", "application/xml"})
	public @ResponseBody PersonDTO getPerson(@PathVariable("id") int id){
		if(log.isDebugEnabled()){
			log.debug("going to query person: " + id );
		}
		Person person = personService.getPerson(id);
		if(person == null){
			if(log.isDebugEnabled()){
				log.debug("can't find person for : " + id );
			}
			throw new EntityNotFoundException("can't find person for : " + id);
		}else{
			return new PersonDTO(person);
		}
	}
	
	@RequestMapping(value = "/ping", 
	method = RequestMethod.GET,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody String ping(){
		if(log.isDebugEnabled()){
			log.debug("going to return ping"  );
		}
		return "{}";

	}
	
	@RequestMapping(value = "/{id}", 
	method = RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.OK)
	public void deletePerson(@PathVariable("id") int id){
		if(log.isDebugEnabled()){
			log.debug("going to delete person: " + id );
		}
		if(id == 99){
			if(log.isDebugEnabled()){
				log.debug("can't delete default person: " + id );
			}
			throw new PersonCantDeleteException(id, "can't delete default person  ");
		}
		Person person = personService.getPerson(id);
		if(person == null){
			if(log.isDebugEnabled()){
				log.debug("can't find person for : " + id );
			}
			throw new EntityNotFoundException("can't find person for : " + id);
		}else{
			return;
		}
	}
	
	// @ResponseBody and ContentNegotiatingViewResolver are two alternatives for the same thing. 
	// You usually use one or the other, not both
	@RequestMapping( 
	method = RequestMethod.GET,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody Map<Integer, PersonDTO> getPersons(){
		if(log.isDebugEnabled()){
			log.debug("going to get all persons" );
		}
		Map<Integer, Person> people = personService.getAll();
		
		Map<Integer, PersonDTO> peopleDTO = new HashMap<Integer, PersonDTO>();
		for(Integer key :people.keySet()){
			Person person = people.get(key);
			PersonDTO personDTO =  new PersonDTO(person);		
			peopleDTO.put(key, personDTO);
		}
		
		return peopleDTO;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody PersonDTO updatePerson(@PathVariable("id") int id, @Valid @RequestBody PersonDTO person){

		if(person.getId() == null ){
			throw new RuntimeException("person id can't be null");
		}
		if(person.getId() != id ){
			throw new RuntimeException("person id didn't match path id");
		}
		
		if(log.isDebugEnabled()){
			log.debug("going to update a person: " + person.getId() + ", " + person.getName());
		}
		personService.updatePerson(person);
		return person;
	}	
	
	/**
	 * @RequestMapping fallback method: 
	 * We can create a fallback method for the controller class 
	 * to make sure we are catching all the client requests 
	 * even though there are no matching handler methods. 
	 * It is useful in sending custom 404 response pages to users 
	 * when there are no handler methods for the request. 
	 */
	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod(HttpServletRequest req){	    
		if(log.isDebugEnabled()){
			log.debug("can't find method for : " + req.getRequestURL() );
		}
	    throw new MethodNotFoundException("can't find method for : " + req.getRequestURL());
	}
	
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo handlePersonNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
         
        String msg = ex.getMessage();         
        return new ErrorInfo(HttpStatus.NOT_FOUND.value(), 4356, msg);
    }

    
    @ExceptionHandler(MethodNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo handleMethodNotFoundException(HttpServletRequest req, MethodNotFoundException ex) {
         
        String msg = ex.getMessage();         
        return new ErrorInfo(HttpStatus.NOT_FOUND.value(), 5674, msg);
    }
}
