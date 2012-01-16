package es.upm.dit.gsi.shanks.model.failure;

/**
 * @author a.carrera
 *
 */
public abstract class NetworkFailure extends Failure {

    /**
	 * I think this class it is not required
	 */
    private static final long serialVersionUID = -838331953844650206L;

    public NetworkFailure(String type) {
        super(type);
    }

}
