package es.upm.dit.gsi.shanks.model.scenario.exception;

public class UnsupportedScenarioStatusException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 6118773200479962191L;
    
    public UnsupportedScenarioStatusException() {
        super("The status is not supported by the Scenario.");
    }

}
