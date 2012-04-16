package es.upm.dit.gsi.shanks.notification.test;

import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.NetworkElementPeriodicEvent;
import es.upm.dit.gsi.shanks.model.event.OneShotEvent;
import es.upm.dit.gsi.shanks.model.event.ScenarioPeriodicEvent;
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
            neped = new TestDevice(TestDefinitions.DEVICE_ID+"NEPE", null, false);
        } catch (UnsupportedNetworkElementStatusException e1) {
            e1.printStackTrace();
        }
        this.addPossibleEventsOfNE(NetworkElementPeriodicEvent.class, neped);
//        TestDevice sped = null;
//        try {
//            sped = new TestDevice(TestDefinitions.DEVICE_ID+"SPE", null, false);
//        } catch (UnsupportedNetworkElementStatusException e1) {
//            e1.printStackTrace();
//        }
//        this.addPossibleEventsOfNE(ScenarioPeriodicEvent.class, sped);
//        TestDevice osed = null;
//        try {
//            osed = new TestDevice(TestDefinitions.DEVICE_ID+"SPE", null, false);
//        } catch (UnsupportedNetworkElementStatusException e1) {
//            e1.printStackTrace();
//        }
//        this.addPossibleEventsOfNE(OneShotEvent.class, osed);
//        this.addPossibleEventsOfScenario(ScenarioPeriodicEvent.class, this);
//        this.addPossibleEventsOfScenario(OneShotEvent.class, this);
    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {
        return null;
    }

}
