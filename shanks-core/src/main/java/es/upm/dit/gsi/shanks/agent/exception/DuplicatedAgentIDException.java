/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Indicates that an agent with the given identifier has been already added to
 * the simulation engine.
 * 
 * @author darofar
 * 
 */
public class DuplicatedAgentIDException extends ShanksException {

    public DuplicatedAgentIDException(String agentID) {
        super("Duplicated ID -> Agent " + agentID);
    }

    private static final long serialVersionUID = -6847538406858490896L;
}
