package es.upm.dit.gsi.shanks.agent.action.exception;

public class UnknownShanksAgentActionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -1884223740174508179L;

    public UnknownShanksAgentActionException(String actionID, String agentID) {
        super("Unknown action " + actionID + "for agent " + agentID);
    }

}
