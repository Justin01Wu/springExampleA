package test.spring.exception;

public class EntityNotFoundException  extends RuntimeException {

	private static final long serialVersionUID = 2344585896L;

	public EntityNotFoundException(String msg){
		super(msg);
	}
}
