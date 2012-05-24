package es.upm.dit.gsi.shanks.model.event.exception;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class UnsupportedElementByEventException extends ShanksEventException {

    public UnsupportedElementByEventException(NetworkElement element) {
        super("The element " + element.getID() + " was not added to the failure, because its class is not a possible affected element.");
    }

    private static final long serialVersionUID = 4943124365421070992L;    
}
