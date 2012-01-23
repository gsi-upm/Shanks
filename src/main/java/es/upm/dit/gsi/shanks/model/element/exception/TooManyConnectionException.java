package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.model.element.link.Link;

public class TooManyConnectionException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -4339179113088736384L;
    
    public TooManyConnectionException(Link l) {
        super("The capacity of the link is full");
    }

}
