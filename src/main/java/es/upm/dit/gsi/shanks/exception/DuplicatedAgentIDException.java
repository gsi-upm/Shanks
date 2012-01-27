package es.upm.dit.gsi.shanks.exception;


public class DuplicatedAgentIDException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 5615795024289903969L;

    public DuplicatedAgentIDException(String agentID) {
        super("There is other agent in the simulation with ID: " + agentID);
    }
}
