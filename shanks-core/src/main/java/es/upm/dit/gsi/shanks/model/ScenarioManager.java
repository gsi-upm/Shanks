package es.upm.dit.gsi.shanks.model;

/**
 * SecenarioManager class
 * 
 * This class generate the possibles errors
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.1
 * 
 */

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sim.engine.SimState;
import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.exception.NoCombinationForFailureException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;

public class ScenarioManager implements Steppable {

    public Logger logger = Logger.getLogger(ScenarioManager.class.getName());

    private static final long serialVersionUID = -7448202235281457216L;

    // DEFAULT STATES OF SCENARIO MANAGER
    private static final int CHECK_FAILURES = 0;
    private static final int GENERATE_FAILURES = 1;

    private Scenario scenario;
    private ScenarioPortrayal portrayal;
    private int simulationStateMachineStatus;

    /**
     * @param scenario
     */
    public ScenarioManager(Scenario scenario, ScenarioPortrayal portrayal) {
        this.logger.setLevel(Level.ALL);
        this.scenario = scenario;
        this.portrayal = portrayal;
    }
    
    /**
     * @return the scenario
     */
    public Scenario getScenario() {
        return this.scenario;
    }

    /**
     * @return the portrayal
     */
    public ScenarioPortrayal getPortrayal() {
        return portrayal;
    }
    
    /* (non-Javadoc)
     * @see sim.engine.Steppable#step(sim.engine.SimState)
     */
    public void step(SimState state) {
        ShanksSimulation sim = (ShanksSimulation) state;
        try {
            this.stateMachine(sim);
        } catch (UnsupportedScenarioStatusException e) {
            logger.severe("UnsupportedScenarioStatusException: " + e.getMessage());
        } catch (NoCombinationForFailureException e) {
            logger.severe("NoCombinationForFailureException: " + e.getMessage());
        }
    }

    /**
     * This method implements the state machine of the scneario manager
     * 
     * @param sim
     * @throws UnsupportedScenarioStatusException 
     * @throws NoCombinationForFailureException 
     */
    public void stateMachine(ShanksSimulation sim) throws UnsupportedScenarioStatusException, NoCombinationForFailureException {
        logger.info("Using default state machine for ScenarioManager");
        switch (this.simulationStateMachineStatus) {
        case CHECK_FAILURES:
            this.checkFailures(sim);
            this.simulationStateMachineStatus = GENERATE_FAILURES;
            break;
        case GENERATE_FAILURES:
            this.generateFailures(sim);
            this.simulationStateMachineStatus = CHECK_FAILURES;
            break;
        }
    }

    /**
     * @param sim
     * @throws UnsupportedScenarioStatusException 
     * @throws NoCombinationForFailureException 
     */
    private void generateFailures(ShanksSimulation sim) throws UnsupportedScenarioStatusException, NoCombinationForFailureException {
        this.scenario.generateFailures();
    }

    /**
     * @param sim
     */
    private void checkFailures(ShanksSimulation sim) {
        List<Failure> resolved = this.scenario.checkResolvedFailures();
        sim.numOfResolvedFailures += resolved.size();
    }
}
