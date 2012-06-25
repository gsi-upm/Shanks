package es.upm.dit.gsi.shanks.model.scenario.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class UnsupportedScenarioStatusException extends ShanksException {

    /**
     * 
     */
    private static final long serialVersionUID = 6118773200479962191L;
    
    public UnsupportedScenarioStatusException() {
        super("The status is not supported by the Scenario.");
    }

}
