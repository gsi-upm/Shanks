/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
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
     * default constructor.
     */
    public ShanksException() {
        super();
    }

    /**
     * This have to bee the more used constructor, cause a ShanksException its
     * really whole prosibilitie of other exceptions. So when need to create one
     * Shanks exception it is necessary to pass to the new exception object all
     * fields and characteristic from the original exception.
     * 
     * @param message
     *            the message of the original exception.
     * @param cause
     *            the original exception object.
     */
    public ShanksException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that just acquire the original message.
     * 
     * @param message
     *            the message of the original exception.
     */
    public ShanksException(String message) {
        super(message);
    }

    /**
     * Constructor that just acquire the original throwable object.
     * 
     * @param cause
     *            the original exception object.
     */
    public ShanksException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = -3745231676898724783L;

}
