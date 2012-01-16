package es.upm.dit.gsi.shanks.model.failure;

/**
 * @author a.carrera
 *
 */
public abstract class ConfigurationFailure extends Failure {

    /**
	 * I think this class it is not required
	 */
    private static final long serialVersionUID = 6278498258201785129L;

    public ConfigurationFailure(String type) {
        super(type);
    }

}
