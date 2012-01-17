package es.upm.dit.gsi.shanks.model.failure.exception;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class UnsupportedElementInFailureException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8058011795056256604L;

    public UnsupportedElementInFailureException(NetworkElement element) {
        super("The element " + element.getID() + " was not added to the failure, because its class is not a possible affected element.");
    }

}
