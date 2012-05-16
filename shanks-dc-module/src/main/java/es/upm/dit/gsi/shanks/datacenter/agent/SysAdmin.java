package es.upm.dit.gsi.shanks.datacenter.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import unbbayes.prs.Node;
import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.datacenter.model.Values;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.DCRouter;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.networkattacks.util.action.RepairComputer;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.Server;

public class SysAdmin extends SimpleShanksAgent implements
		BayesianReasonerShanksAgent {

	private String bayesianNetworkFilePath;
	private ProbabilisticNetwork bayesianNetwork;
	
	/**
	 * Previous logs
	 */
	//private String logStatus;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4001442333333480506L;

	public SysAdmin(String id) {
		super(id);
		//this.logStatus = Values.SYSADMIN_LOG_OK;
		this.bayesianNetworkFilePath = Values.SYSADMIN_BAYESIAN_NETWORK_PATH;
		try {
			this.bayesianNetwork = ShanksAgentBayesianReasoningCapability
					.loadNetwork(this.bayesianNetworkFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void checkMail() {
		// TODO Auto-generated method stub

	}

	@Override
	public ProbabilisticNetwork getBayesianNetwork() {
		return this.bayesianNetwork;
	}

	@Override
	public void setBayesianNetwork(ProbabilisticNetwork bn) {
		this.bayesianNetwork = bn;
		// this.bayesianNetworkFilePath
	}

	@Override
	public String getBayesianNetworkFilePath() {
		return this.bayesianNetworkFilePath;
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		try {
			String to_repair = getBrokenCount(simulation);
			Server webServer = (Server)((ComplexScenario)((ComplexScenario)simulation.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID);
			//HashMap<String, Boolean> serverStatus = webServer.getStatus();
			String webServerLoad = null;
			if(webServer.getProperties().containsKey(Server.PROPERTY_LOAD)){
				int load = (Integer)webServer.getProperties().get(Server.PROPERTY_LOAD);
				if (load > 100) {
					webServerLoad = Values.SYSADMIN_LOAD_DOWN;
				} else if ( load > 80) {
					webServerLoad = Values.SYSADMIN_LOAD_HIGH;
				} else {
					webServerLoad = Values.SYSADMIN_LOAD_NORMAL;
				}
				
			}
//			for(String key: serverStatus.keySet()){
//				System.out.println(key);
//				if (serverStatus.get(key))
//					webServerLoad = key;
//			}
			
			// DCRouter Load
			RouterDNS router = (RouterDNS)((ComplexScenario)simulation.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID).getNetworkElement(Values.ENTERPRISE_GATEWAY_ID);
			//HashMap<String, Boolean> routerStatus = router.getStatus();
			String routerLoad = null;
			if(router.getProperties().containsKey(RouterDNS.PROPERTY_CONGESTION)){
				double load = (Double) router.getProperties().get(RouterDNS.PROPERTY_CONGESTION);
				if (load > 100) {
					routerLoad = Values.SYSADMIN_LOAD_DOWN;
				} else if ( load > 80) {
					routerLoad = Values.SYSADMIN_LOAD_HIGH;
				} else {
					routerLoad = Values.SYSADMIN_LOAD_NORMAL;
				}
				
			}
			
//			for(String key: routerStatus.keySet()){
//				if (routerStatus.get(key))
//					routerLoad = key;
//			}
			int logWeird;
			if (webServer.getProperties().containsKey(Server.PROPERTY_LOG)) {
				logWeird = (Integer)webServer.getProperty(Server.PROPERTY_LOG);
			} else {
				logWeird = 8;
			}
			
			String logStatus;
			if (logWeird > Values.SERVER_LOG_NOK){
				logStatus = Values.SYSADMIN_LOG_NOK;
			} else if(logWeird > Values.SERVER_LOG_WEIRD){
				logStatus = Values.SYSADMIN_LOG_WEIRD;
			} else {
				logStatus = Values.SYSADMIN_LOG_OK;
			}
	

			// Set the evidences. 
			if(to_repair != String.valueOf(Integer.MIN_VALUE))
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						Values.SYSADMIN_REPAIR_NODENAME, to_repair);
			
			if(webServerLoad != null)
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						Values.SYSADMIN_SERVER_LOAD_NODENAME, webServerLoad);
			
			if (routerLoad != null)
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						Values.SYSADMIN_ROUTER_LOAD_NODENAME, routerLoad);
			
			if (logStatus != null)
				ShanksAgentBayesianReasoningCapability.addEvidence(this,
						Values.SYSADMIN_LOG_NODENAME, logStatus);

			this.bayesianNetwork.updateEvidences();

			HashMap<String, Float> hypotheses = ShanksAgentBayesianReasoningCapability
					.getNodeStatesHypotheses(this,
							Values.SYSADMIN_ACTION_NODENAME);
			Iterator<String> actions = hypotheses.keySet().iterator();
			int randomResult = simulation.random.nextInt(100);

			int probability = 0;
			String action = (String) Values.ACTION_NOT_AVAILABLE;

			while (probability < randomResult) {
				action = actions.next();
				probability += hypotheses.get(action) * 100;
			}
			executeAction(action, simulation);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void executeAction(String action, ShanksSimulation sim) {
		if (action.equalsIgnoreCase(Values.SYSADMIN_ACTION_LOOKOUT)) {
			lookOut(sim);
		} else if (action.equalsIgnoreCase(Values.SYSADMIN_ACTION_MAINTENANCE)) {
			maintenance(sim);
		} else if (action.equalsIgnoreCase(Values.SYSADMIN_ACTION_PATCH)) {
			patch(sim);
		} else if (action.equalsIgnoreCase(Values.SYSADMIN_ACTION_REPAIR)) {
			repair(sim);
		} else {
			// Do nothing, action not recognized
			sim.logger.warning("Sysadmin action " + action + " not recognized");
		}
	}

	/**
	 * 
	 * @param sim
	 */
	private void lookOut(ShanksSimulation sim) {
		try {
		//The ID to be banned
		String webID = "";
		Server webServer;
		
			webServer = (Server)((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID);
			int logWeird;
			if (webServer.getProperties().containsKey(Server.PROPERTY_LOG)) {
				logWeird = (Integer)webServer.getProperty(Server.PROPERTY_LOG);
			} else {
				logWeird = 8;
			}
			
		if (logWeird > Values.SERVER_LOG_WEIRD){
			HashMap<String, Integer> webLogs = webServer.getLogs();
			for (String ID : webLogs.keySet()){
				if (webID.equals("")){
					webID = ID;
				} else {
					if (webLogs.get(ID) > webLogs.get(webID))
						webID = ID;
				}
			}
		}
		String sqlID = "";
		Server sqlServer = (Server)sim.getScenario().getNetworkElement(Values.WEB_SERVER_ID);
		int sqlLog;
		
		System.out.println("*********************************************** " + sqlServer ==  null);
		if (sqlServer.getProperties().containsKey(Server.PROPERTY_LOG)) {
			sqlLog = (Integer)sqlServer.getProperty(Server.PROPERTY_LOG);
		} else {
			sqlLog = 8;
		}
		
		if (sqlLog > Values.SERVER_LOG_WEIRD){
			HashMap<String, Integer> sqlLogs = sqlServer.getLogs();
			for (String ID : sqlLogs.keySet()){
				if (sqlID.equals("")){
					sqlID = ID;
				} else {
					if (sqlLogs.get(ID) > sqlLogs.get(webID))
						sqlID = ID;
				}
			}
		}
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: ban the IDs
	}

	/**
	 * Doing the maintenance on a server will reduce its load
	 * a third.
	 * 
	 * @param ShanksSimulation
	 *            - The simulation
	 */
	private void maintenance(ShanksSimulation sim) {
		// TODO: Implement maintenance
		// Updates the router vulnerability
		RouterDNS mainRouter = null;
		Server webServer = null;
		Server sqlServer = null;
		try {
			mainRouter = (RouterDNS) ((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.ENTERPRISE_GATEWAY_ID);
			webServer = (Server)((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID);
			sqlServer = (Server) ((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.SQL_SERVER_ID);
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//DCRouter load
		if(mainRouter != null){
			HashMap<String, Object> routerProperties = new HashMap<String, Object>();
			routerProperties
					.put(DCRouter.PROPERTY_CONGESTION, ((Double) mainRouter
							.getProperty(DCRouter.PROPERTY_CONGESTION)) *2/3);
			mainRouter.setProperties(routerProperties);
		}

		// Updates the server vulnerability
		if (webServer != null) {
			HashMap<String, Object> webServerProperties = new HashMap<String, Object>();
			webServerProperties
					.put(Server.PROPERTY_LOAD, ((Double) webServer
							.getProperty(Server.PROPERTY_LOAD)) *2/3);
			webServer.setProperties(webServerProperties);
		}

		// Updates the sqlServer
		if (sqlServer !=  null) {
			HashMap<String, Object> sqlServerProperties = new HashMap<String, Object>();
			sqlServerProperties
					.put(Server.PROPERTY_LOAD, ((Double) sqlServer
							.getProperty(Server.PROPERTY_LOAD)) *2/3);
			sqlServer.setProperties(sqlServerProperties);
		}
	}

	/**
	 * Patching a server will reduce its posibility of being hacked in half.
	 * 
	 * 
	 * @param ShanksSimulation
	 *            - The simulation
	 */
	private void patch(ShanksSimulation sim) {
		// TODO: Implement patch
		RouterDNS mainRouter = null;
		Server webServer = null;
		Server sqlServer = null;
		try {
			mainRouter = (RouterDNS) ((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.ENTERPRISE_GATEWAY_ID);
			webServer = (Server)((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.EXTERNAL_SERVICES_SERVER_ID);
			sqlServer = (Server) ((ComplexScenario)((ComplexScenario)sim.getScenario())
					.getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.DATA_CENTER_SCENARIO_ID)
					.getNetworkElement(Values.SQL_SERVER_ID);
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Updates the router vulnerability
		if (mainRouter != null) {
			HashMap<String, Object> routerProperties = new HashMap<String, Object>();
			routerProperties
					.put(DCRouter.PROPERTY_VULNERABILITY, ((Double) mainRouter
							.getProperty(DCRouter.PROPERTY_VULNERABILITY)) / 2);
			mainRouter.setProperties(routerProperties);
		}

		// Updates the server vulnerability
		if(webServer !=null) {
		HashMap<String, Object> webServerProperties = new HashMap<String, Object>();
			webServerProperties
				.put(Server.PROPERTY_VULNERABILITY, ((Double) webServer
							.getProperty(Server.PROPERTY_VULNERABILITY)) / 2);
			webServer.setProperties(webServerProperties);
		}

		// Updates the sqlServer
		if(sqlServer != null) {
			HashMap<String, Object> sqlServerProperties = new HashMap<String, Object>();
			sqlServerProperties
					.put(Server.PROPERTY_VULNERABILITY, ((Double) sqlServer
							.getProperty(Server.PROPERTY_VULNERABILITY)) / 2);
			sqlServer.setProperties(sqlServerProperties);
		}
	}

	private void repair(ShanksSimulation sim) {
		RepairComputer action = new RepairComputer(this.getID() + "Repair", this);
		// Is this the correct way??
		try {
			action.executeAction(sim, this, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the count of current failures in the worker room.
	 * 
	 * @param ShanksSimulation - The simulation
	 * @return int - MIN_VALUE if something goes wrong
	 */
	private String getBrokenCount(ShanksSimulation sim){
		try {
			String fail = Values.SYSADMIN_REPAIR_OK;
			int failures = 0;
			for (int i = 0; i < Values.NUMBER_OF_WORKERROOMS; i++){
				Scenario workerRoom = ((ComplexScenario)((ComplexScenario)sim.getScenario()).getScenario(Values.ENTERPRISE_SCENARIO_ID)).getScenario(Values.WORKER_ROOM_SCENARIO_ID + i);
				Set<Failure> failuresSet = workerRoom.getCurrentFailures();
				failures += failuresSet.size();
			}
			if (failures > 3) {
				fail = Values.SYSADMIN_REPAIR_MORE;
			} else if (failures > 0) {
				fail = Values.SYSADMIN_REPAIR_1_3;
			}
			
			return fail;
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(Integer.MIN_VALUE);
	}
}
