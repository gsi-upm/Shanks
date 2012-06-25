package es.upm.dit.gsi.shanks.exception;

/**
 * This exception include all possible exceptions that can be generated on the
 * hole simulation framework. It is intended to unify all throws exception
 * declaration to make statements more legible.
 * 
 * @author darofar
 * 
 */
public class ShanksException extends Exception {

    /**
     * 
     */
    public ShanksException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public ShanksException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ShanksException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ShanksException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -3745231676898724783L;

}
