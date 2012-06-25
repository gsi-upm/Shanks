package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class UnkownAgentException extends ShanksException {

    public UnkownAgentException(String agentID) {
        super("There is not any agent with ID: " + agentID + " in the simulation");
    }

    private static final long serialVersionUID = -3884089264695032818L;
}
