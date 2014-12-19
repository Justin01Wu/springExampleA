package test.spring.dto;

import java.util.Date;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import test.spring.beans.Person;

//@Component // Spring traditional way to set an entity
@Named    // JSR spec way to set an entity
@XmlRootElement(name="person")
//if your code can support json but can NOT support XML, 
//it means you need add @XmlRootElement to your returned bean
public class PersonDTO extends Person {
	
	public PersonDTO(){		
	}
	
	public PersonDTO(Person person){
		if(person == null){
			throw new IllegalArgumentException("person can't be null");
		}		
		BeanUtils.copyProperties(person, this);
	}
	
	@JsonIgnore
	@Override
	public Date getBirth() {
		return super.getBirth();
	}
}
