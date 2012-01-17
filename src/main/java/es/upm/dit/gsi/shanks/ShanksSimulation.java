package es.upm.dit.gsi.shanks;

import java.util.logging.Logger;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;

/**
 * Model class
 * 
 * This class represents the model which manage all the simulation
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public abstract class ShanksSimulation extends SimState {

    private static final long serialVersionUID = -2238530527253654867L;

    public Logger logger = Logger.getLogger(ShanksSimulation.class.getName());

    public static SparseGrid2D problems;

    private ScenarioManager scenarioManager;

    public int numOfResolvedFailures;

    /**
     * @param seed
     */
    public ShanksSimulation(long seed) {
        super(seed);
        this.scenarioManager = this.createScenarioManager();
    }

    /**
     * This method will set all required information about Scenario
     *
     * @return the completed Scenario object
     */
    abstract public ScenarioManager createScenarioManager();
    
    /**
     * @return
     */
    public ScenarioManager getScenarioManager() {
        return this.scenarioManager;
    }
    
    /**
     * @return
     */
    public Scenario getScenario() {
        return this.scenarioManager.getScenario();
    }
    
    /**
     * @return
     */
    public ScenarioPortrayal getPortrayal() {
        return this.scenarioManager.getPortrayal();
    }

    /* (non-Javadoc)
     * @see sim.engine.SimState#start()
     */
    @Override
    public void start() {
        super.start();
        logger.finer("-> start method");
        startSimulation();
    }

    /**
     * The initial configuration of the scenario
     */
    public void startSimulation() {
        schedule.scheduleRepeating(Schedule.EPOCH, 0, this.scenarioManager, 2);
        this.addSteppables();
//        schedule.scheduleRepeating(Schedule.EPOCH + 1, 2, a, 2);

    }
    
    /**
     * It is required to add steppables
     * 
     * @return
     */
    public Schedule getSchedule() {
        return schedule;
    }
    

    /**
     * This method is called during the start phase of the simulation. The command:
     * schedule.scheduleRepeating(Schedule.EPOCH, 0, this.scenarioManager, 2);
     * is always executed in the first place.
     */
    abstract public void addSteppables();

    /**
     * @param args
     */
    public static void main(String[] args) {
        doLoop(ShanksSimulation.class, args);
        System.exit(0);
    }

}
