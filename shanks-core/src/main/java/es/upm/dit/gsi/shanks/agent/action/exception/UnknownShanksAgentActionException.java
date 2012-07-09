/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.action.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Signals that a non-defined action identified with actionID has been called from
 * a specific agent identified with an agentID. 
 * 
 * @author -
 *
 */
public class UnknownShanksAgentActionException extends ShanksException {

    /**
     * 
     * @param actionID 
     *              The undefined action required. 
     * @param agentID 
     *              Agent which was called to perform the undefined action.   
     */
    public UnknownShanksAgentActionException(String actionID, String agentID) {
        super("Unknown action " + actionID + "for agent " + agentID);
    }

    private static final long serialVersionUID = -1884223740174508179L;
}
