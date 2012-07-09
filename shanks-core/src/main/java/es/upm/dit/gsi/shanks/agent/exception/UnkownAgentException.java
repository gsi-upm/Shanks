/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Indicates that an agent with the corresponding ID cannot be found on the hole
 * simulation.
 * 
 * @author darofar
 * 
 */
public class UnkownAgentException extends ShanksException {

    public UnkownAgentException(String agentID) {
        super("There is not any agent with ID: " + agentID
                + " in the simulation");
    }

    private static final long serialVersionUID = -3884089264695032818L;
}
