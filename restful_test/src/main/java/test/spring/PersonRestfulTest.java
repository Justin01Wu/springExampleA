package test.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import test.spring.beans.Person;
import test.spring.beans.ValidationErrorDTO;
import test.spring.dto.PersonDTO;
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

		ResponseEntity<PersonDTO> entity = template.getForEntity(
				ulrBase + "/{id}", PersonDTO.class, person.getId());

		assertEquals(HttpStatus.OK, entity.getStatusCode());
		Person result = entity.getBody();

		assertEquals(result.getName(), person.getName() );
		assertEquals(result.getId(), person.getId());		
	}	

	//@Test
	public void testGetPerson() {
		Person person = new Person(99, "sample Person", "abcD1234");
		verifyPerson(person);
	}
	
	//@Test
	public void testaaa() throws UnsupportedEncodingException {
		byte[] xxx = {60, 104, 116, 109, 108, 62, 60, 104, 101, 97, 100, 62, 60, 116, 105, 116, 108, 101, 62, 65, 112, 97, 99, 104, 101, 32, 84, 111, 109, 99, 97, 116, 47, 55, 46, 48, 46, 53, 53, 32, 45, 32, 69, 114, 114, 111, 114, 32, 114, 101, 112, 111, 114, 116, 60, 47, 116, 105, 116, 108, 101, 62, 60, 115, 116, 121, 108, 101, 62, 60, 33, 45, 45, 72, 49, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 99, 111, 108, 111, 114, 58, 119, 104, 105, 116, 101, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 45, 99, 111, 108, 111, 114, 58, 35, 53, 50, 53, 68, 55, 54, 59, 102, 111, 110, 116, 45, 115, 105, 122, 101, 58, 50, 50, 112, 120, 59, 125, 32, 72, 50, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 99, 111, 108, 111, 114, 58, 119, 104, 105, 116, 101, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 45, 99, 111, 108, 111, 114, 58, 35, 53, 50, 53, 68, 55, 54, 59, 102, 111, 110, 116, 45, 115, 105, 122, 101, 58, 49, 54, 112, 120, 59, 125, 32, 72, 51, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 99, 111, 108, 111, 114, 58, 119, 104, 105, 116, 101, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 45, 99, 111, 108, 111, 114, 58, 35, 53, 50, 53, 68, 55, 54, 59, 102, 111, 110, 116, 45, 115, 105, 122, 101, 58, 49, 52, 112, 120, 59, 125, 32, 66, 79, 68, 89, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 99, 111, 108, 111, 114, 58, 98, 108, 97, 99, 107, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 45, 99, 111, 108, 111, 114, 58, 119, 104, 105, 116, 101, 59, 125, 32, 66, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 99, 111, 108, 111, 114, 58, 119, 104, 105, 116, 101, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 45, 99, 111, 108, 111, 114, 58, 35, 53, 50, 53, 68, 55, 54, 59, 125, 32, 80, 32, 123, 102, 111, 110, 116, 45, 102, 97, 109, 105, 108, 121, 58, 84, 97, 104, 111, 109, 97, 44, 65, 114, 105, 97, 108, 44, 115, 97, 110, 115, 45, 115, 101, 114, 105, 102, 59, 98, 97, 99, 107, 103, 114, 111, 117, 110, 100, 58, 119, 104, 105, 116, 101, 59, 99, 111, 108, 111, 114, 58, 98, 108, 97, 99, 107, 59, 102, 111, 110, 116, 45, 115, 105, 122, 101, 58, 49, 50, 112, 120, 59, 125, 65, 32, 123, 99, 111, 108, 111, 114, 32, 58, 32, 98, 108, 97, 99, 107, 59, 125, 65, 46, 110, 97, 109, 101, 32, 123, 99, 111, 108, 111, 114, 32, 58, 32, 98, 108, 97, 99, 107, 59, 125, 72, 82, 32, 123, 99, 111, 108, 111, 114, 32, 58, 32, 35, 53, 50, 53, 68, 55, 54, 59, 125, 45, 45, 62, 60, 47, 115, 116, 121, 108, 101, 62, 32, 60, 47, 104, 101, 97, 100, 62, 60, 98, 111, 100, 121, 62, 60, 104, 49, 62, 72, 84, 84, 80, 32, 83, 116, 97, 116, 117, 115, 32, 53, 48, 48, 32, 45, 32, 82, 101, 113, 117, 101, 115, 116, 32, 112, 114, 111, 99, 101, 115, 115, 105, 110, 103, 32, 102, 97, 105, 108, 101, 100, 59, 32, 110, 101, 115, 116, 101, 100, 32, 101, 120, 99, 101, 112, 116, 105, 111, 110, 32, 105, 115, 32, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 108, 108, 101, 103, 97, 108, 83, 116, 97, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 58, 32, 97, 114, 103, 117, 109, 101, 110, 116, 32, 116, 121, 112, 101, 32, 109, 105, 115, 109, 97, 116, 99, 104, 60, 47, 104, 49, 62, 60, 72, 82, 32, 115, 105, 122, 101, 61, 34, 49, 34, 32, 110, 111, 115, 104, 97, 100, 101, 61, 34, 110, 111, 115, 104, 97, 100, 101, 34, 62, 60, 112, 62, 60, 98, 62, 116, 121, 112, 101, 60, 47, 98, 62, 32, 69, 120, 99, 101, 112, 116, 105, 111, 110, 32, 114, 101, 112, 111, 114, 116, 60, 47, 112, 62, 60, 112, 62, 60, 98, 62, 109, 101, 115, 115, 97, 103, 101, 60, 47, 98, 62, 32, 60, 117, 62, 82, 101, 113, 117, 101, 115, 116, 32, 112, 114, 111, 99, 101, 115, 115, 105, 110, 103, 32, 102, 97, 105, 108, 101, 100, 59, 32, 110, 101, 115, 116, 101, 100, 32, 101, 120, 99, 101, 112, 116, 105, 111, 110, 32, 105, 115, 32, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 108, 108, 101, 103, 97, 108, 83, 116, 97, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 58, 32, 97, 114, 103, 117, 109, 101, 110, 116, 32, 116, 121, 112, 101, 32, 109, 105, 115, 109, 97, 116, 99, 104, 60, 47, 117, 62, 60, 47, 112, 62, 60, 112, 62, 60, 98, 62, 100, 101, 115, 99, 114, 105, 112, 116, 105, 111, 110, 60, 47, 98, 62, 32, 60, 117, 62, 84, 104, 101, 32, 115, 101, 114, 118, 101, 114, 32, 101, 110, 99, 111, 117, 110, 116, 101, 114, 101, 100, 32, 97, 110, 32, 105, 110, 116, 101, 114, 110, 97, 108, 32, 101, 114, 114, 111, 114, 32, 116, 104, 97, 116, 32, 112, 114, 101, 118, 101, 110, 116, 101, 100, 32, 105, 116, 32, 102, 114, 111, 109, 32, 102, 117, 108, 102, 105, 108, 108, 105, 110, 103, 32, 116, 104, 105, 115, 32, 114, 101, 113, 117, 101, 115, 116, 46, 60, 47, 117, 62, 60, 47, 112, 62, 60, 112, 62, 60, 98, 62, 101, 120, 99, 101, 112, 116, 105, 111, 110, 60, 47, 98, 62, 32, 60, 112, 114, 101, 62, 111, 114, 103, 46, 115, 112, 114, 105, 110, 103, 102, 114, 97, 109, 101, 119, 111, 114, 107, 46, 119, 101, 98, 46, 117, 116, 105, 108, 46, 78, 101, 115, 116, 101, 100, 83, 101, 114, 118, 108, 101, 116, 69, 120, 99, 101, 112, 116, 105, 111, 110, 58, 32, 82, 101, 113, 117, 101, 115, 116, 32, 112, 114, 111, 99, 101, 115, 115, 105, 110, 103, 32, 102, 97, 105, 108, 101, 100, 59, 32, 110, 101, 115, 116, 101, 100, 32, 101, 120, 99, 101, 112, 116, 105, 111, 110, 32, 105, 115, 32, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 108, 108, 101, 103, 97, 108, 83, 116, 97, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 58, 32, 97, 114, 103, 117, 109, 101, 110, 116, 32, 116, 121, 112, 101, 32, 109, 105, 115, 109, 97, 116, 99, 104, 10, 72, 97, 110, 100, 108, 101, 114, 77, 101, 116, 104, 111, 100, 32, 100, 101, 116, 97, 105, 108, 115, 58, 32, 10, 67, 111, 110, 116, 114, 111, 108, 108, 101, 114, 32, 91, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 80, 101, 114, 115, 111, 110, 65, 112, 105, 93, 10, 77, 101, 116, 104, 111, 100, 32, 91, 112, 117, 98, 108, 105, 99, 32, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 100, 116, 111, 46, 80, 101, 114, 115, 111, 110, 68, 84, 79, 32, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 80, 101, 114, 115, 111, 110, 65, 112, 105, 46, 99, 114, 101, 97, 116, 101, 80, 101, 114, 115, 111, 110, 40, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 100, 116, 111, 46, 80, 101, 114, 115, 111, 110, 68, 84, 79, 41, 93, 10, 82, 101, 115, 111, 108, 118, 101, 100, 32, 97, 114, 103, 117, 109, 101, 110, 116, 115, 58, 32, 10, 91, 48, 93, 32, 91, 116, 121, 112, 101, 61, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 98, 101, 97, 110, 115, 46, 80, 101, 114, 115, 111, 110, 93, 32, 91, 118, 97, 108, 117, 101, 61, 116, 101, 115, 116, 46, 115, 112, 114, 105, 110, 103, 46, 98, 101, 97, 110, 115, 46, 80, 101, 114, 115, 111, 110, 64, 51, 52, 49, 97, 50, 56, 99, 51, 93, 10, 10, 9, 111, 114, 103, 46, 115, 112, 114, 105, 110, 103, 102, 114, 97, 109, 101, 119, 111, 114, 107, 46, 119, 101, 98, 46, 115, 101, 114, 118, 108, 101, 116, 46, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 112, 114, 111, 99, 101, 115, 115, 82, 101, 113, 117, 101, 115, 116, 40, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 106, 97, 118, 97, 58, 57, 55, 51, 41, 10, 9, 111, 114, 103, 46, 115, 112, 114, 105, 110, 103, 102, 114, 97, 109, 101, 119, 111, 114, 107, 46, 119, 101, 98, 46, 115, 101, 114, 118, 108, 101, 116, 46, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 100, 111, 80, 111, 115, 116, 40, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 106, 97, 118, 97, 58, 56, 54, 51, 41, 10, 9, 106, 97, 118, 97, 120, 46, 115, 101, 114, 118, 108, 101, 116, 46, 104, 116, 116, 112, 46, 72, 116, 116, 112, 83, 101, 114, 118, 108, 101, 116, 46, 115, 101, 114, 118, 105, 99, 101, 40, 72, 116, 116, 112, 83, 101, 114, 118, 108, 101, 116, 46, 106, 97, 118, 97, 58, 54, 52, 54, 41, 10, 9, 111, 114, 103, 46, 115, 112, 114, 105, 110, 103, 102, 114, 97, 109, 101, 119, 111, 114, 107, 46, 119, 101, 98, 46, 115, 101, 114, 118, 108, 101, 116, 46, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 115, 101, 114, 118, 105, 99, 101, 40, 70, 114, 97, 109, 101, 119, 111, 114, 107, 83, 101, 114, 118, 108, 101, 116, 46, 106, 97, 118, 97, 58, 56, 51, 55, 41, 10, 9, 106, 97, 118, 97, 120, 46, 115, 101, 114, 118, 108, 101, 116, 46, 104, 116, 116, 112, 46, 72, 116, 116, 112, 83, 101, 114, 118, 108, 101, 116, 46, 115, 101, 114, 118, 105, 99, 101, 40, 72, 116, 116, 112, 83, 101, 114, 118, 108, 101, 116, 46, 106, 97, 118, 97, 58, 55, 50, 55, 41, 10, 9, 111, 114, 103, 46, 97, 112, 97, 99, 104, 101, 46, 116, 111, 109, 99, 97, 116, 46, 119, 101, 98, 115, 111, 99, 107, 101, 116, 46, 115, 101, 114, 118, 101, 114, 46, 87, 115, 70, 105, 108, 116, 101, 114, 46, 100, 111, 70, 105, 108, 116, 101, 114, 40, 87, 115, 70, 105, 108, 116, 101, 114, 46, 106, 97, 118, 97, 58, 53, 50, 41, 10, 60, 47, 112, 114, 101, 62, 60, 47, 112, 62, 60, 112, 62, 60, 98, 62, 114, 111, 111, 116, 32, 99, 97, 117, 115, 101, 60, 47, 98, 62, 32, 60, 112, 114, 101, 62, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 108, 108, 101, 103, 97, 108, 83, 116, 97, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 58, 32, 97, 114, 103, 117, 109, 101, 110, 116, 32, 116, 121, 112, 101, 32, 109, 105, 115, 109, 97, 116, 99, 104, 10, 72, 97, 110, 100, 108, 101, 114, 77, 101, 116, 104, 111, 100, 32, 100, 101, 116, 97, 105, 108, 115, 58, 32, 10, 67, 111, 110, 116, 114, 111, 108, 108, 101, 114, 32, 91, 116, 101, 115, 116};
		String decoded = new String(xxx, "UTF-8");
		System.out.println(decoded);

	}
	
	
	
	@Test
	public void testCreatePerson() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate template = new RestTemplate();

		Person person = new Person(0, "Person" + new Date().getTime(), "DFg#$rt645478");
		PersonDTO personDTO  = new PersonDTO(person);
		ResponseEntity<PersonDTO> entity = template.postForEntity(
				ulrBase , personDTO, PersonDTO.class);
		
		//String path = entity.getHeaders().getLocation().getPath();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		//assertTrue(path.startsWith("/aggregators/orders/"));
		Person result = entity.getBody();

		 System.out.println ("The Person ID is " + result.getId());
		// System.out.println ("The Location is " +
		// entity.getHeaders().getLocation());

		assertEquals(result.getName(), person.getName() );
		assertEquals(result.getPassword(), person.getPassword() );
		
		//verifyPerson(result);

	}
	
	//@Test
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
	
	//@Test
	public void testExtraFieldForCreatePerson() {
	

        String extraFieldJson = "{\"name\":\"ccccc\",\"password\":\"abcd1234\",\"dfd\":\"ddffgg\"} ";
        ValidationErrorDTO result =  getError( extraFieldJson);

		System.out.println ("you have error messages: " + result.getGeneralMsg());

		assertTrue(result.getGeneralMsg().startsWith("Could not read JSON: Unrecognized field \"dfd\""));

	}
	
	//@Test
	public void testWrongFormatJson() {

        String wrongJson = "\"name\":\"ccccc\",\"password\":\"abcd1234\",\"dfd\":\"ddffgg\"} ";  // missed first {
        
        ValidationErrorDTO result =  getError( wrongJson);

		System.out.println ("you have error messages: " + result.getGeneralMsg());

		assertTrue(result.getGeneralMsg().startsWith("Could not read JSON: Can not instantiate value of type"));

	}
	
}
