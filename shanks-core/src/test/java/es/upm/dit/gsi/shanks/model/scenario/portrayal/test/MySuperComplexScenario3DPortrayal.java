package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MySuperComplexScenario3DPortrayal extends
        ComplexScenario3DPortrayal {

    public MySuperComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length)
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height, length);
    }

    @Override
    public void placeScenarios() throws DuplicatedPortrayalID {
        // TODO Auto-generated method stub

    }

    @Override
    public void placeElements() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setupPortrayals() {
        // TODO Auto-generated method stub

    }

}
