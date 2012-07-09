/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * Indicates that a display object with the given identifier has been already
 * added to the simulation grpahics engine.
 * 
 * @author darofar
 * 
 */
public class DuplictaedDisplayIDException extends ShanksException {

    public DuplictaedDisplayIDException(String displayID) {
        super("DisplayID " + displayID + " already exists.");
    }

    private static final long serialVersionUID = -43234124242135011L;
}
