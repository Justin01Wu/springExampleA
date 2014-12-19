package test.spring.dto;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import test.spring.beans.Person;

public class PersonDTOTest {

	@Test
	public void testPersonDTO() {
		
		Date date = new Date();
		Person person = new Person(99, "sample Person", "abcD1234");
		person.setBirth(date);
		person.addEmails("wuyg719@gmail.com");
		person.addEmails("justin1.wu@gmail.com");

		
		PersonDTO pd = new PersonDTO(person);
		
		assertEquals(pd.getName(), "sample Person");
		assertEquals(pd.getPassword(), "abcD1234");
		assertEquals(pd.getBirth(), date);
		assertEquals(pd.getEmails().size(), 2);
	}

}
