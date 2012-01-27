package es.upm.dit.gsi.shanks.agent.exception;

public class DuplicatedActionIDException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3198634183647976648L;

    public DuplicatedActionIDException(String actionID, String agentID) {
        super("Duplicated ID -> Action: " + actionID + " in Agent " + agentID);
    }
}
