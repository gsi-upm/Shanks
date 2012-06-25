package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class DuplictaedDisplayIDException extends ShanksException {

    public DuplictaedDisplayIDException(String displayID) {
        super("DisplayID " + displayID + " already exists.");
    }

    private static final long serialVersionUID = -43234124242135011L;
}
