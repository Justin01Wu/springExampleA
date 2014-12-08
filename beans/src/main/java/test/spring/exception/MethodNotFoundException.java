package test.spring.exception;

public class MethodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2344585896L;

	public MethodNotFoundException(String msg){
		super(msg);
	}
}
