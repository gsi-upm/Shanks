package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class DuplicatedAgentIDException extends ShanksException {


    public DuplicatedAgentIDException(String agentID) {
        super("Duplicated ID -> Agent " + agentID);
    }
    
    private static final long serialVersionUID = -6847538406858490896L;
}
