package es.upm.dit.gsi.shanks.model.scenario.portrayal.exception;

public class DuplicatedPortrayalID extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8192222874695344303L;

    public DuplicatedPortrayalID(String displayID, String portrayalName) {
        super("PotrayalID " + portrayalName + " already exists in display " + displayID);
    }

}
