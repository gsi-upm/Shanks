package es.upm.dit.gsi.shanks;

import java.util.logging.Logger;

import sim.engine.Schedule;
import sim.engine.SimState;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
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

    private ScenarioManager scenarioManager;

    private int numOfResolvedFailures;

    /**
    /**
     * @param seed
     */
    public ShanksSimulation(long seed) {
        super(seed);
        try {
            this.scenarioManager = this.createScenarioManager();
        } catch (UnsupportedNetworkElementStatusException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TooManyConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedScenarioStatusException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DuplicatedIDException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This method will set all required information about Scenario
     * 
     * @return the completed Scenario object
     * @throws DuplicatedIDException
     * @throws UnsupportedScenarioStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedNetworkElementStatusException
     */
    abstract public ScenarioManager createScenarioManager()
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException;

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
    public ScenarioPortrayal getScenarioPortrayal() {
        ScenarioPortrayal sp = this.scenarioManager.getPortrayal();
        while (sp==null) {
            sp = this.scenarioManager.getScenario().createScenarioPortrayal();
            this.scenarioManager.setPortrayal(sp);
        }
        return sp;
    }

    /**
     * @return the numOfResolvedFailures
     */
    public int getNumOfResolvedFailures() {
        return numOfResolvedFailures;
    }

    /**
     * @param numOfResolvedFailures the numOfResolvedFailures to set
     */
    public void setNumOfResolvedFailures(int numOfResolvedFailures) {
        this.numOfResolvedFailures = numOfResolvedFailures;
    }

    /*
     * (non-Javadoc)
     * 
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
        // schedule.scheduleRepeating(Schedule.EPOCH + 1, 2, a, 2);

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
     * This method is called during the start phase of the simulation. The
     * command: schedule.scheduleRepeating(Schedule.EPOCH, 0,
     * this.scenarioManager, 2); is always executed in the first place.
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
