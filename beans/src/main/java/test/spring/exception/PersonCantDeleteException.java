package test.spring.exception;

public class PersonCantDeleteException extends RuntimeException {

	private static final long serialVersionUID = 2344585896L;
	
	private int id;

	public PersonCantDeleteException(int id, String msg){
		super(msg);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
