/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;

/**
 * Indicates that the corresponding Network Element doesn't know or supports the
 * given field name.
 * 
 * @author darofar
 * 
 */
public class UnsupportedNetworkElementFieldException extends ShanksException {

    public UnsupportedNetworkElementFieldException(NetworkElement element,
            String field) {
        super("The field " + field + " is not supported by "
                + element.getClass().toString() + ".");
    }

    private static final long serialVersionUID = 8739490073386103417L;
}
