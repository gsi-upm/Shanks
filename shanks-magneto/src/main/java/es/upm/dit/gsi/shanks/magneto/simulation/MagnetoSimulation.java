package es.upm.dit.gsi.shanks.magneto.simulation;

import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class MagnetoSimulation extends ShanksSimulation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700007277544949836L;
	
	private Properties configuration;
    public static final String CONFIGURATION = "Configuration";


	public MagnetoSimulation(long seed, Class<? extends Scenario> scenarioClass,
            String scenarioID, String initialState, Properties properties,
            Properties configPropertiesLANSimulation) throws ShanksException {
		
		super(seed, scenarioClass, scenarioID, initialState, properties);
		this.configuration = configPropertiesLANSimulation;

	}

}
