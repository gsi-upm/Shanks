package es.upm.dit.gsi.shanks.exception;

public class DuplictaedDisplayIDException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -43234124242135011L;

    public DuplictaedDisplayIDException(String displayID) {
        super("DisplayID " + displayID + " already exists.");
    }

}
