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
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class ScenarioManager implements Steppable {

    public Logger logger = Logger.getLogger(ScenarioManager.class.getName());

    private static final long serialVersionUID = -7448202235281457216L;

    // STATES OF SCENARIO MANAGER
    private static final int CHECK_FAILURES = 0;
    private static final int GENERATE_FAILURES = 1;

    private Scenario scenario;
    private int simulationStateMachineStatus;

    /**
     * @param scenario
     */
    public ScenarioManager(Scenario scenario) {
        this.logger.setLevel(Level.ALL);
        this.scenario = scenario;
    }

    // The actions done by the agent for each step
    // TODO chekc this method
    public void step(SimState state) {
        ShanksSimulation sim = (ShanksSimulation) state;
        this.stateMachine(sim);
    }

    public void stateMachine(ShanksSimulation sim) {
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
     */
    private void generateFailures(ShanksSimulation sim) {
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
