package es.upm.dit.gsi.shanks.datacenter.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {
	

	
	
	public Values(ShanksSimulation sim) {
		super(sim);
	}
	// Failures probability
	public static final double SERVER_FAILURE_PROB = 0.001;
	
	// Network Elements names. 
	public static final String SA_COMPUTER_ID = "SysAdminComputer#";
	public static final String LDAP_SERVER_ID = "LDAPServer";
	public static final String EXTERNAL_SERVICES_SERVER_ID = "ExternalServer";
	public static final String BBDD_SERVER_ID = "BBDDServer";
	public static final String BBDD_REPLICA_ID = "BBDDReplica";
//	public static final String WEB_APP_ID = "Web App Server";
	public static final String WEB_PROXY_ID = "WebProxy";
	public static final String INTRANET_LINK = "IntranetBusLink";
	public static final String ETHERNET_ID = "Ethernet#";
	
	// Scenario Values. 
	public static final double ETHERNET_LENGHT = 2.5;
	public static final int WIFI_CHANNELS = 64;
	public static final int NUMBER_OF_SYSADMINS = 3;
	

	// SysAdmin values
	public static final String SYSADMIN_BAYESIAN_NETWORK_PATH = "../shanks-network-attacks-simulation/src/main/java/es/upm/dit/gsi/shanks/networkattacks/util/Hacker.net";
	
	public static final String SYSADMIN_REPAIR_NODENAME = "Reparar";
	public static final String SYSADMIN_SERVER_LOAD_NODENAME = "Server_load";
	public static final String SYSADMIN_ROUTER_LOAD_NODENAME = "Router_load";
	public static final String SYSADMIN_LOG_NODENAME = "log";
	public static final String SYSADMIN_ACTION_NODENAME = "Action";
	
	public static final String SYSADMIN_LOG_OK = "OK";
	public static final String SYSADMIN_LOG_WEIRD = "Weird";
	public static final String SYSADMIN_LOG_NOK = "NOK";
	
	public static final String SYSADMIN_ACTION_REPAIR = "Repair";
	public static final String SYSADMIN_ACTION_MAINTENANCE = "Maintenance";
	public static final String SYSADMIN_ACTION_PATCH = "Patch";
	public static final String SYSADMIN_ACTION_LOOKOUT = "Look_out";



}
