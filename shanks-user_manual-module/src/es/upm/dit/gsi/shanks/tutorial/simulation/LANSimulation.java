package es.upm.dit.gsi.shanks.tutorial.simulation;

import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

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

}
