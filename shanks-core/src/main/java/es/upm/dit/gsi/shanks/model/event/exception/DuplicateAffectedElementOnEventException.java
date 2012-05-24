package es.upm.dit.gsi.shanks.model.event.exception;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class DuplicateAffectedElementOnEventException extends ShanksEventException {

    public DuplicateAffectedElementOnEventException(NetworkElement element) {
        super("The element " + element.getID() + " was not added to the failure, because there is duplicated.");
    }

    private static final long serialVersionUID = 4903802993554363971L;

}
