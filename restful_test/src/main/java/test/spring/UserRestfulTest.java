package test.spring;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import test.spring.beans.User;

public class UserRestfulTest {
	private static final String ulrBase = "http://localhost:8080/restful/users";

	private void verifyUser(User user) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		RestTemplate template = new RestTemplate();

		ResponseEntity<User> entity = template.getForEntity(
				ulrBase + "/{id}", User.class, user.getId());

		assertEquals(HttpStatus.OK, entity.getStatusCode());
		User result = entity.getBody();

		assertEquals(result.getLastName(), user.getLastName() );
		assertEquals(result.getFirstName(), user.getFirstName() );
	}	
	
	@Test
	public void testCreateUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		RestTemplate template = new RestTemplate();

		User user = new User();
		user.setFirstName("Justin");
		user.setLastName("Wu");
		user.setPassWord("password");
		user.setConfirmPassword("password");
		
		ResponseEntity<User> entity = template.postForEntity(ulrBase , user, User.class);
		
		//String path = entity.getHeaders().getLocation().getPath();

		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		//assertTrue(path.startsWith("/aggregators/orders/"));
		User result = entity.getBody();

		 System.out.println ("The User ID is " + result.getId());
		// System.out.println ("The Location is " +
		// entity.getHeaders().getLocation());
		
		verifyUser(result);

	}

}
