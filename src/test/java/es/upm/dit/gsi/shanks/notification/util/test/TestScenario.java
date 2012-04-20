package es.upm.dit.gsi.shanks.notification.util.test;

import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.networkelement.PeriodicNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.networkelement.ProbabilisticNetworkElementEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.PeriodicScenarioEvent;
import es.upm.dit.gsi.shanks.model.event.scenario.ProbabilisticScenarioEvent;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class TestScenario extends Scenario{

    public static final String TEST_STATE = "sunny";

    public TestScenario(String id, String initialState, Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException {
        super(id, initialState, properties);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Scenario2DPortrayal createScenario2DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return null;
    }

    @Override
    public Scenario3DPortrayal createScenario3DPortrayal()
            throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
        return null;
    }

    @Override
    public void setPossibleStates() {
        this.addPossibleStatus(TestScenario.TEST_STATE);
    }

    @Override
    public void addNetworkElements()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, DuplicatedIDException {
    }

    @Override
    public void addPossibleFailures() {
    }

    @Override
    public void addPossibleEvents() {
        TestDevice neped = null;
        try {
            neped = new TestDevice(TestDefinitions.DEVICE_ID+"NEProbE", null, false);
            this.addPossibleEventsOfNE(PeriodicNetworkElementEvent.class, neped);
            
            TestDevice sped = new TestDevice(TestDefinitions.DEVICE_ID+"NEPerE", null, false);
            this.addPossibleEventsOfNE(ProbabilisticNetworkElementEvent.class, sped);
            
            this.addPossibleEventsOfScenario(ProbabilisticScenarioEvent.class, this);
            this.addPossibleEventsOfScenario(PeriodicScenarioEvent.class, this);
            
        } catch (UnsupportedNetworkElementStatusException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {
        return null;
    }
}
