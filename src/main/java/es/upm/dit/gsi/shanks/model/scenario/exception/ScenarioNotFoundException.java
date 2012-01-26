package es.upm.dit.gsi.shanks.model.scenario.exception;

public class ScenarioNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 3756498160120399005L;
    
    public ScenarioNotFoundException(String scenarioID, String complexScenarioID) {
        super("Scenario " + scenarioID + " not found in the ComplexSceanario " + complexScenarioID);
    }

}
