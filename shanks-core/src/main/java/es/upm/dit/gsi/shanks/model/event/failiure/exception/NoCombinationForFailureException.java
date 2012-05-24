package es.upm.dit.gsi.shanks.model.event.failiure.exception;

import es.upm.dit.gsi.shanks.model.event.exception.ShanksEventException;
import es.upm.dit.gsi.shanks.model.event.failiure.Failure;

public class NoCombinationForFailureException extends ShanksEventException {

    public NoCombinationForFailureException(Failure failure) {
        super("There is no possible affected elements for failure class: " + failure.getClass().getName() + " FailureID: " + failure.getID());
    }
    
    private static final long serialVersionUID = 2759811079998338628L;
}
