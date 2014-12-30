package test.spring;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import test.spring.beans.Person;
import test.spring.dto.StudentDTO;
import test.spring.exception.EntityNotFoundException;
import test.spring.exception.WriteReadOnlyPropertyException;
import test.spring.service.PersonService;

@Controller
@RequestMapping("/readOnlyProp")
public class ReadOnlyPropApi {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@Inject @Named("default23")
	private PersonService personService;  
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody StudentDTO createPerson(@Valid @RequestBody StudentDTO student){

		if(log.isDebugEnabled()){
			log.debug("going to save a new person: " + student.getName());
		}
		personService.savePerson(student);
		return student;
	}
	

	// only support json  
	// the default is json if accept is blank
	@RequestMapping(value = "/{id}", method = RequestMethod.GET	)
	public @ResponseBody StudentDTO getPerson(@PathVariable("id") int id){
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
			StudentDTO student = new StudentDTO(person);
			student.resetHash();
			return student;
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT,
	headers ={"Accept=application/json"},
	produces={"application/json"})
	public @ResponseBody StudentDTO updatePerson(@PathVariable("id") int id, @Valid @RequestBody StudentDTO student){

		if(student.getId() == null ){
			throw new RuntimeException("student id can't be null");
		}
		if(student.getId() != id ){
			throw new RuntimeException("student id didn't match path id");
		}
		
		if(student.getHash() != student.calculateNewHash()){
			// without this part, 'version' will be silently ignored because it doesn't have setter method
			// without this part, 'name' will be updated
			throw new WriteReadOnlyPropertyException("student readonlyProperties has been changed");
		}
		
		if(log.isDebugEnabled()){
			log.debug("going to update a student: " + student.getId() + ", " + student.getName());
		}
		personService.updatePerson(student);
		return student;
	}	
}
