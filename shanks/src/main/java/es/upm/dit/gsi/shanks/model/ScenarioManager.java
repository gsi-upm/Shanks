package es.upm.dit.gsi.shanks.model;

/**
 * SecenarioManager class
 * 
 * This class generate the possibles errors
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.Simulation;
import es.upm.dit.gsi.shanks.agent.Agent;
import es.upm.dit.gsi.shanks.model.device.Device;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

import sim.engine.SimState;
import sim.engine.Steppable;

public class ScenarioManager implements Steppable {

    public Logger log = Logger.getLogger(this.getClass().toString());

    private static final long serialVersionUID = -7448202235281457216L;
    public static Scenario scenario;
    public static Failure dev;
    public static int totalproblems = 0;

    public ScenarioManager(Scenario scen) {
        this.log.setLevel(Level.ALL);
        scenario = scen;
    }

    public void generateProblem() {
        if (Agent.repairFlag) {
            repairProblems();
        }
        int randomproblem = (int) (Math.random() * Failure.deverrors.size());
        double randomerrorgenerator = Math.random();
        if (randomerrorgenerator <= Simulation.PROB_BROKEN) {
            dev = Failure.deverrors.get(randomproblem);
            dev.setTrigger(true);
            Simulation.problems.setObjectLocation(dev, 25, 25);
            Agent.problemDetected = dev.getName();
            totalproblems++;
        } else if (randomerrorgenerator > Simulation.PROB_BROKEN) {

            // TODO create a valid failure
            // Error noproblem = new Failure ("No problem", true);
            // dev = noproblem;
            Simulation.problems.setObjectLocation(dev, 25, 25);
            Agent.problemDetected = dev.getName();
        }
        System.out.println("ERROR " + dev.getName());
        System.out.println("TOTAL PROBLEMS " + totalproblems);
    }

    public void repairProblems() {
        for (Failure d : Failure.deverrors) {
            d.setTrigger(false);
        }
    }

    // The actions done by the agent for each step
    public void step(SimState state) {
        System.out.println("NUEVA PRUEBA");
        Simulation model = (Simulation) state;
        switch (model.selectError()) {
        case 0:
            model.setBrokenStatus();
            break;
        case 1:
            generateProblem();
            Failure.setDeviceWithProblems();
            break;
        }
    }
}
