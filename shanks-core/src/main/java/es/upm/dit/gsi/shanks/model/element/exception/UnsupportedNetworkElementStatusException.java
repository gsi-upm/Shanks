package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class UnsupportedNetworkElementStatusException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8739490073386103417L;

    public UnsupportedNetworkElementStatusException(NetworkElement element, String status) {
        super("The status " + status +  " is not supported by " + element.getClass().toString() + " .");
    }
    
}
