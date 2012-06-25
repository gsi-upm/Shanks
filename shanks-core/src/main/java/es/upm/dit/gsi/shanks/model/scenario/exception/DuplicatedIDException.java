package es.upm.dit.gsi.shanks.model.scenario.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class DuplicatedIDException extends ShanksException {

    public DuplicatedIDException(NetworkElement element) {
        super("The network element ID " + element.getID() + " already is in the scenario.");
    }

    private static final long serialVersionUID = -9004190779438179905L;
}
