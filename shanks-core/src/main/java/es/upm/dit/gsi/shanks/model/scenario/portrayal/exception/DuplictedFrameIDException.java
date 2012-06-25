/**
 * 
 */
package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

/**
 * @author a.carrera
 *
 */
public class DuplictedFrameIDException extends ShanksException {

    public DuplictedFrameIDException(String frameID) {
        super("There is a duplication with frame ID: " + frameID);
    }

    private static final long serialVersionUID = -5088324036301062822L;
}