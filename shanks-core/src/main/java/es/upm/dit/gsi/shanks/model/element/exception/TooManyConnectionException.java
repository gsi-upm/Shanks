/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

/**
 * Indicates when a link has been tried to connect over his capacity of
 * connections.
 * 
 * @author darofar
 * 
 */
public class TooManyConnectionException extends ShanksException {

    public TooManyConnectionException(Link l) {
        super("The capacity of the link is full");
    }

    private static final long serialVersionUID = -4339179113088736384L;
}
