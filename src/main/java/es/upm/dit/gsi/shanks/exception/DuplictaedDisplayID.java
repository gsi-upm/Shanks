package es.upm.dit.gsi.shanks.exception;

public class DuplictaedDisplayID extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -43234124242135011L;

    public DuplictaedDisplayID(String displayID) {
        super("DisplayID " + displayID + " already exists.");
    }

}
