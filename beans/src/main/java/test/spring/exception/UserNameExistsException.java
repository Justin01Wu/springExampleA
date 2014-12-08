package test.spring.exception;

public class UserNameExistsException extends RuntimeException {

	private static final long serialVersionUID = 2344585896L;

	private String fieldName;
	
	public UserNameExistsException(String msg, String fieldName){
		super(msg);
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
}
