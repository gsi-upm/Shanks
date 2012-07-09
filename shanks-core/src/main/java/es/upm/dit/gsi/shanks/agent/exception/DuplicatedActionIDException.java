/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Indicates that an action with the given identifier has been already added to
 * the simulation engine.
 * 
 * @author darofar
 * 
 */
public class DuplicatedActionIDException extends ShanksException {

    public DuplicatedActionIDException(String actionID, String agentID) {
        super("Duplicated ID -> Action: " + actionID + " in Agent " + agentID);
    }
    
    private static final long serialVersionUID = -3198634183647976648L;
}
