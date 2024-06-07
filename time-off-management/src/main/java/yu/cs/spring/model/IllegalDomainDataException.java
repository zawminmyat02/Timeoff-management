package yu.cs.spring.model;

public class IllegalDomainDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalDomainDataException(String message) {
		super(message);
	}

}
