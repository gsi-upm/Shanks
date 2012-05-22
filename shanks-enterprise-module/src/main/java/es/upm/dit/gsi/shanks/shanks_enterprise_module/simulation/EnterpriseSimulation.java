package es.upm.dit.gsi.shanks.shanks_enterprise_module.simulation;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.exception.DuplicatedActionIDException;
import es.upm.dit.gsi.shanks.exception.DuplicatedAgentIDException;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.workerroom.agent.RepairWireAgent;

public class EnterpriseSimulation extends ShanksSimulation{

	private static final long serialVersionUID = 1778288778609950190L;
    private Properties configuration;

    public static final String CONFIGURATION = "Configuration";
	
	public EnterpriseSimulation(long seed,
			Class<? extends Scenario> scenarioClass, String scenarioID,
			String initialState, Properties properties,
			Properties configPropertiesEnterpriseSimulation)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			UnsupportedNetworkElementFieldException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, DuplicatedPortrayalIDException,
			ScenarioNotFoundException, DuplicatedAgentIDException,
			DuplicatedActionIDException {
		super(seed, scenarioClass, scenarioID, initialState, properties);
		this.configuration = configPropertiesEnterpriseSimulation;
	}
	
	public void registerShanksAgents() throws DuplicatedAgentIDException,
    		DuplicatedActionIDException {
		RepairWireAgent worker = new RepairWireAgent("Repair Wire Worker");
		this.registerShanksAgent(worker);
	}

}
