package test.spring.exception;

public class WriteReadOnlyPropertyException extends RuntimeException {

	private static final long serialVersionUID = 234576896L;

	public WriteReadOnlyPropertyException(String msg){
		super(msg);
	}
}