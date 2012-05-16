package es.upm.dit.gsi.shanks.networkattacks.util;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values {
	
	public static ShanksSimulation sim;
	

	/**
	 * @param sim
	 */
	public Values(ShanksSimulation sim) {
		this.setSim(sim);
	}

	/**
	 * @param sim the sim to set
	 */
	public void setSim(ShanksSimulation newSim) {
		Values.sim = newSim;
	}
	
	//Services values.
	public static final String SERVICE_NOT_FOUND = "ServiceNotFound";
	public static final Object ACTION_NOT_AVAILABLE = "NotAvailableAction";
	
	// HAN
	public static final String HAN_SCENARIO_ID = "HomeAreaNetwork#";
	
	// Values for network elements properties. 
	public static final String DISCONNECTED = "disconnected";
	public static final String CONNECTED = "connected";
	public static final String ON = "on";
	public static final String OFF = "off";
	public static final String NO_IP = "no-ip";
	public static final String NA = "not-aplicable"; //whenever the value of an state or property is Not Applicable.
	
	// DNS values
	public static final String DNSConsultID = "DNSConsult#";
	
	// Failures probability
	public static final Double COMPUTER_FAILURE_PROB = 0.1;
	public static final Double NO_IP_FAILURE_PROB = 0.5;
	public static final Double NO_ISP_FAILURE_PROB = 0.1;
	public static final Double ROUTER_FAILURE_PROB = 0.1;
	public static final Double WIRELESSD_FAILURE_PROB = 0.1;
	
	// Portrayals Values.
	public static final double Computer2DSide = 25;
	public static final double Server2DHeight = 50;
	
	//Router IDs 
	public static final String ISP_GATEWAY_ID = "ISPGateway";
	public static final String HAN_ROUTER_ID = "WifiRouter#";
	public static final String ENTERPRISE_GATEWAY_ID = "MegaCorpGateway";
	public static final String DATA_CENTER_ROUTER_ID = "DataCenterRouter";
	public static final String WORKER_ROOM_ROUTER_ID = "WorkerRoomRouter";
	
	// Enterprise net elements
	public static final String ENTERPRISE_SCENARIO_ID = "MegaCorp";
	public static final String DATA_CENTER_SCENARIO_ID = "DataCenter";
	public static final String WEB_SERVER_ID = "MegaCorpWebServer";
	public static final String SQL_SERVER_ID = "MegaCorpSQLServer";
	
	// Enterprise server properties
	public static final int SERVER_LOG_OK = 0;
	public static final int SERVER_LOG_WEIRD = 5;
	public static final int SERVER_LOG_NOK = 10;
	
	// Simulation Configuration
	public static final int NUMBER_OF_WORKERROOMS = 3;
	public static final int NUMBER_OF_HANS = 8;
	public static final double HACKER_PROBABILITY = 0.5;
	
	//IDs for charts
	public static final String DDOS_NUMBER = "Number of DDoS Attacks";
	public static final String ROOT_NUMBER =" Number of RootShell Attacks";
	public static final String SQL_NUMBER = "Number of SQLInjection Attacks";

	//Agents
	public static final String COMPLAINT = "UserComplaint";
	public static final String UNBLOCK_PETITION = "UserUnblockPetition";

}