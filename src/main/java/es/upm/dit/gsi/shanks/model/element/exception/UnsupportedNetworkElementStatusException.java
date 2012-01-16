package es.upm.dit.gsi.shanks.model.element.exception;

public class UnsupportedNetworkElementStatusException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8739490073386103417L;

    public UnsupportedNetworkElementStatusException() {
        super("The status is not supported by the Network Element.");
    }
    
}
