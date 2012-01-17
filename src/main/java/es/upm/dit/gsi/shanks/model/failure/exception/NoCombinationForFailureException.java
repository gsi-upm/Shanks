package es.upm.dit.gsi.shanks.model.failure.exception;

import es.upm.dit.gsi.shanks.model.failure.Failure;

public class NoCombinationForFailureException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -908269688569274769L;
    
    public NoCombinationForFailureException(Failure failure) {
        super("There is no possible affected elements for failure class: " + failure.getClass().getName() + " FailureID: " + failure.getID());
    }

}
