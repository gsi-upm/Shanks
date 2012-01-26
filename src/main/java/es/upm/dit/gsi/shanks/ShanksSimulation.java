package es.upm.dit.gsi.shanks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Logger;

import sim.engine.Schedule;
import sim.engine.SimState;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * Model class
 * 
 * This class represents the model which manage all the simulation
 * 
 * @author Daniel Lara
 * @version 0.1
 * 
 */

public class ShanksSimulation extends SimState {

    private static final long serialVersionUID = -2238530527253654867L;

    public Logger logger = Logger.getLogger(ShanksSimulation.class.getName());

    private ScenarioManager scenarioManager;

    private int numOfResolvedFailures;
    
    
    /**
     * @param seed
     * @param scenarioClass
     * @param scenarioID
     * @param initialState
     * @param properties
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnsupportedNetworkElementStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws DuplicatedPortrayalIDException 
     * @throws ScenarioNotFoundException 
     */
    public ShanksSimulation(long seed, Class<? extends Scenario> scenarioClass, String scenarioID, String initialState, Properties properties) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException, DuplicatedPortrayalIDException, ScenarioNotFoundException {
        super(seed);
            this.scenarioManager = this.createScenarioManager(scenarioClass, scenarioID, initialState, properties);
    }

    /**
     * This method will set all required information about Scenario
     * 
     * @return the completed Scenario object
     * @throws DuplicatedIDException
     * @throws UnsupportedScenarioStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedNetworkElementStatusException
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IllegalArgumentException 
     * @throws DuplicatedPortrayalIDException 
     * @throws ScenarioNotFoundException 
     */
    private ScenarioManager createScenarioManager(Class<? extends Scenario> scenarioClass, String scenarioID, String initialState, Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, DuplicatedPortrayalIDException, ScenarioNotFoundException {
        
        Constructor<? extends Scenario> c  = scenarioClass.getConstructor(new Class[]{String.class,String.class,Properties.class});
       
        Scenario s = c.newInstance(scenarioID,initialState,properties);
        logger.fine("Scenario created");
        ScenarioPortrayal sp = s.createScenarioPortrayal();
        if (sp==null) {
            logger.warning("ScenarioPortrayals is null");
        }
        ScenarioManager sm = new ScenarioManager(s, sp);
        return sm;
    }

    /**
     * @return ScenarioManager of the simulation
     */
    public ScenarioManager getScenarioManager() {
        return this.scenarioManager;
    }

    /**
     * @return Scenario of the simulation
     */
    public Scenario getScenario() {
        return this.scenarioManager.getScenario();
    }

    /**
     * @return ScenarioPortrayal of the scenario of the simulation
     * @throws DuplicatedPortrayalIDException 
     * @throws ScenarioNotFoundException 
     */
    public ScenarioPortrayal getScenarioPortrayal() throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
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
     * It is required to add Steppables
     * 
     * @return Schedule of the simulation
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * This method is called during the start phase of the simulation. The
     * command: schedule.scheduleRepeating(Schedule.EPOCH, 0,
     * this.scenarioManager, 2); is always executed in the first place.
     */
    public void addSteppables() {
        logger.info("No steppables added...");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        doLoop(ShanksSimulation.class, args);
        System.exit(0);
    }

}
