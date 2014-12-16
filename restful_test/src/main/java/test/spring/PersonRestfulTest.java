package test.spring;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import test.spring.beans.Person;
import test.spring.beans.ValidationErrorDTO;
import test.spring.validator.PasswordValidatorImpl;
import test.spring.validator.UserNameValidatorImpl;


/**
 * test my restful API, the web server must be started and target URL must be http://localhost:8080/restful/
 *
 */
public class PersonRestfulTest  {
	
	private static final String ulrBase = "http://localhost:8080/restful/persons";
	
	private void verifyPerson(Person person) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		RestTemplate template = new RestTemplate();

		ResponseEntity<Person> entity = template.getForEntity(
				ulrBase + "/{id}", Person.class, person.getId());

		assertEquals(HttpStatus.OK, entity.getStatusCode());
		Person result = entity.getBody();

		assertEquals(result.getName(), person.getName() );
		assertEquals(result.getId(), person.getId());		
	}	

	@Test
	public void testGetPerson() {
		Person person = new Person(99, "sample Person", "abcD1234");
		verifyPerson(person);
	}
	
	@Test
	public void testCreatePerson() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate template = new RestTemplate();

		Person person = new Person(0, "Person" + new Date().getTime(), "DFg#$rt6454");
		
		ResponseEntity<Person> entity = template.postForEntity(
				ulrBase , person, Person.class);
		
		//String path = entity.getHeaders().getLocation().getPath();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		//assertTrue(path.startsWith("/aggregators/orders/"));
		Person result = entity.getBody();

		 System.out.println ("The Person ID is " + result.getId());
		// System.out.println ("The Location is " +
		// entity.getHeaders().getLocation());

		assertEquals(result.getName(), person.getName() );
		assertEquals(result.getPassword(), person.getPassword() );
		
		verifyPerson(result);

	}
	
	@Test
	public void testBadRequestForCreatePerson() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate template = new RestTemplate();
		
		template.setErrorHandler(new MyResponseErrorHandler());		
		// use special error handler to avoid postForEntity throw exception

		Person person = new Person(0, "Person:" + new Date().getTime(), "abcd1234");
		
		ResponseEntity<ValidationErrorDTO> entity = template.postForEntity(
				ulrBase , person, ValidationErrorDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
		ValidationErrorDTO result = entity.getBody();

		 System.out.println ("you have error messages: " + result.getFieldErrors().size());
		 assertEquals(result.getFieldErrors().size(), 2);
		 assertEquals(result.getFieldErrors().get("name").get(0), UserNameValidatorImpl.errorMsg);
		 assertEquals(result.getFieldErrors().get("password").get(0), PasswordValidatorImpl.errorMsg);
	}
	
	private static ValidationErrorDTO getError(String jsonStr) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
	    HttpEntity<String> requestEntity = new HttpEntity<String>(jsonStr, headers);
	    
		RestTemplate template = new RestTemplate();			
		template.setErrorHandler(new MyResponseErrorHandler());		
		// use special error handler to avoid postForEntity throw exception
				    
		ResponseEntity<ValidationErrorDTO> error = template.exchange(ulrBase, 
				HttpMethod.POST, requestEntity, ValidationErrorDTO.class);


		assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
		ValidationErrorDTO result = error.getBody();
		return result;
		
	}
	
	@Test
	public void testExtraFieldForCreatePerson() {
	

        String extraFieldJson = "{\"name\":\"ccccc\",\"password\":\"abcd1234\",\"dfd\":\"ddffgg\"} ";
        ValidationErrorDTO result =  getError( extraFieldJson);

		System.out.println ("you have error messages: " + result.getGeneralMsg());

		assertTrue(result.getGeneralMsg().startsWith("Could not read JSON: Unrecognized field \"dfd\""));

	}
	
	@Test
	public void testWrongFormatJson() {

        String wrongJson = "\"name\":\"ccccc\",\"password\":\"abcd1234\",\"dfd\":\"ddffgg\"} ";  // missed first {
        
        ValidationErrorDTO result =  getError( wrongJson);

		System.out.println ("you have error messages: " + result.getGeneralMsg());

		assertTrue(result.getGeneralMsg().startsWith("Could not read JSON: Can not instantiate value of type"));

	}
	
}
