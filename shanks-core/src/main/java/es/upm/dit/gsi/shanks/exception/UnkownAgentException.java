package es.upm.dit.gsi.shanks.exception;

public class UnkownAgentException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -3884089264695032818L;
    
    public UnkownAgentException(String agentID) {
        super("There is not any agent with ID: " + agentID + " in the simulation");
    }

}
