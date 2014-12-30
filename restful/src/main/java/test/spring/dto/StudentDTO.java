package test.spring.dto;

import org.springframework.beans.BeanUtils;

import test.spring.beans.Person;

public class StudentDTO extends Person implements ReadOnlyAPI{
	
	private int version;
	private int hash;
	
	public StudentDTO(){		
	}
	
	public StudentDTO(Person person){
		if(person == null){
			throw new IllegalArgumentException("person can't be null");
		}		
		BeanUtils.copyProperties(person, this);
	}
	
	public int getVersion(){
		return version;
	}
	
	public int calculateNewHash(){
		return (this.getId() + ";"+ this.getVersion()+";"+  this.getName()).hashCode();
	}
	
	public int getHash(){
		return hash;
	}	
	
	public void resetHash(){
		hash = calculateNewHash();
	}

}