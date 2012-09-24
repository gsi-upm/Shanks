package es.upm.dit.gsi.shanks.tutorial.simulation;

import java.util.Properties;

import sim.engine.Schedule;
import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.test.MyShanksSimulation;
import es.upm.dit.gsi.shanks.model.test.steppable.FailureLog;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresChartPainter;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresGUI;
import es.upm.dit.gsi.shanks.tutorial.agent.CyberManagerAgent;

/**
 * 
 * @author Daniel Lara
 *
 */

public class LANSimulation extends ShanksSimulation{

	private static final long serialVersionUID = 8523661290500701914L;
	
	 private Properties configuration;
	 public static final String CONFIGURATION = "Configuration";

	public LANSimulation(long seed, Class<? extends Scenario> scenarioClass,
			String scenarioID, String initialState, Properties properties,
			Properties configPropertiesLANSimulation)
			throws ShanksException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
        this.configuration = configPropertiesLANSimulation;

	}
	
	
	 /*
     * (non-Javadoc)
     * 
     * @see es.upm.dit.gsi.shanks.ShanksSimulation#addSteppables()
     */
    @Override
    public void addSteppables() {
        int conf = new Integer(
                this.configuration
                        .getProperty(MyShanksSimulation.CONFIGURATION));
        switch (conf) {
        case 0:
            logger.fine("Nothing to do here... No more steppables");
            break;
        case 1:
            Steppable steppable = new FailureLog();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, steppable, 500);
            break;
        case 2:
            Steppable failuresgui = new FailuresGUI();
            schedule.scheduleRepeating(Schedule.EPOCH, 4, failuresgui, 1);
            break;
        case 3:
            Steppable chart = new FailuresChartPainter();
            schedule.scheduleRepeating(Schedule.EPOCH, 3, chart, 50);
            Steppable failures = new FailuresGUI();
            schedule.scheduleRepeating(Schedule.EPOCH, 4, failures, 1);
            break;
        default:
            logger.info("No configuration for MyShanksSimulation. Configuration 0 loaded -> default");
        }

    }
	
	
	public void registerShanksAgents() throws ShanksException{
		CyberManagerAgent agent1 = new CyberManagerAgent("Cyber Manager");
		this.registerShanksAgent(agent1);
	}

}
