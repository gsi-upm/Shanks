package es.upm.dit.gsi.shanks.model.element.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;

public class UnsupportedNetworkElementFieldException extends ShanksException {
    
    public static final String STATUS = "status";
    public static final String PROPERTY = "property";
    public static final String FIELD = "field";

    public UnsupportedNetworkElementFieldException(NetworkElement element, String field) {
        super("The "+FIELD+" "+ field +  " is not supported by " + element.getClass().toString() + ".");
    }
    
    public UnsupportedNetworkElementFieldException(NetworkElement element, String type, String field) {
        super("The "+type+" "+field+" is not supported by " + element.getClass().toString() + ".");
    }
    
    private static final long serialVersionUID = 8739490073386103417L;
}
