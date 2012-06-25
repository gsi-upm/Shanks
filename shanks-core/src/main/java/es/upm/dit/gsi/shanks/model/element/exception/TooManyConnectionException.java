package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.link.Link;

public class TooManyConnectionException extends ShanksException {

    public TooManyConnectionException(Link l) {
        super("The capacity of the link is full");
    }

    private static final long serialVersionUID = -4339179113088736384L;
}