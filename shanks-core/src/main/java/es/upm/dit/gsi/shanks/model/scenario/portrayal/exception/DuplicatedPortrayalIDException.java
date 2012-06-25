package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class DuplicatedPortrayalIDException extends ShanksException {

    public DuplicatedPortrayalIDException(String displayID, String portrayalName) {
        super("PotrayalID " + portrayalName + " already exists in display " + displayID);
    }

    private static final long serialVersionUID = -8192222874695344303L;
}
