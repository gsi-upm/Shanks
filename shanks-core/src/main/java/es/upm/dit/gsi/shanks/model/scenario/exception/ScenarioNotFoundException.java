package es.upm.dit.gsi.shanks.model.scenario.exception;

import es.upm.dit.gsi.shanks.exception.ShanksException;

public class ScenarioNotFoundException extends ShanksException {

    public ScenarioNotFoundException(String scenarioID, String complexScenarioID) {
        super("Scenario " + scenarioID + " not found in the ComplexSceanario " + complexScenarioID);
    }

    private static final long serialVersionUID = 3756498160120399005L;
}
