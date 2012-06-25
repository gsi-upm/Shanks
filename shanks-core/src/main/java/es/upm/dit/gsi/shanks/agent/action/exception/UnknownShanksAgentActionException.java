package es.upm.dit.gsi.shanks.agent.action.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class UnknownShanksAgentActionException extends ShanksException {

    public UnknownShanksAgentActionException(String actionID, String agentID) {
        super("Unknown action " + actionID + "for agent " + agentID);
    }

    private static final long serialVersionUID = -1884223740174508179L;
}
