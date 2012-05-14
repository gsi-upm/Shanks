package es.upm.dit.gsi.shanks.datacenter.model;

public class Values {
	
	// Values for network elements properties. 
	public static final String DISCONNECTED = "disconnected";
	public static final String CONNECTED = "connected";
	public static final String ON = "on";
	public static final String OFF = "off";
	
	//whenever the value of an state or property is Not Applicable.
	public static final String NA = "not-aplicable";
	
	//
	public static final String NO_IP = "no-ip";
	
	
	// Failures probability
	public static final Double COMPUTER_FAILURE_PROB = 0.1;
	public static final Double NO_IP_FAILURE_PROB = 0.5;
	public static final Double NO_ISP_FAILURE_PROB = 0.1;
	public static final Double ROUTER_FAILURE_PROB = 0.1;
	public static final Double WIRELESSD_FAILURE_PROB = 0.1;
	public static final double SERVER_FAILURE_PROB = 0.001;
	
	// Network Elements names. 
	public static final String COMPUTER_ID = "ITCrowComputer#";
	public static final String DATA_CENTER_ROUTER_ID = "DataCenterRouter#";
	public static final String SERVER_ID = "Server#";
	public static final String LDAP_SERVER_ID = "LDAPServer";
	public static final String EXTERNAL_SERVER_ID = "ExternalServer";
	public static final String BBDD_SERVER_ID = "BBDDServer";
	public static final String BBDD_REPLICA_ID = "BBDDReplica";
	public static final String ETHERNET_ID = "Ethernet#";
	public static final String WEB_APP_ID= "Web App Server";
	public static final String WEB_PROXY_ID="WebProxy";
	
	// Scenario Values. 
	public static final double ETHERNET_LENGHT = 2.5;
	public static final int WIFI_CHANNELS = 64;
	public static final int NUMBER_OF_ITCROW = 3;
	
	// Portrayals Values.
	public static final double Computer2DSide = 25;
	public static final double Server2DHeight = 50;

	//Services values.
	public static final String SERVICE_NOT_FOUND = "ServiceNotFound";
	public static final Object ACTION_NOT_AVAILABLE = "NotAvailableAction";

	// SysAdmin values
	public static final String SYSADMIN_BAYESIAN_NETWORK_PATH = "/shanks-dc-module/src/main/java/es/upm/dit/gsi/shanks/datacenter/agent/SysAdmin.net";
	
	public static final String SYSADMIN_REPAIR_NODENAME = "Reparar";
	public static final String SYSADMIN_SERVER_LOAD_NODENAME = "Server_load";
	public static final String SYSADMIN_ROUTER_LOAD_NODENAME = "Router_load";
	public static final String SYSADMIN_LOG_NODENAME = "log";
	public static final String SYSADMIN_ACTION_NODENAME = "Action";
	
	public static final String SYSADMIN_LOG_OK = "OK";
	public static final String SYSADMIN_LOG_WEIRD = "Weird";
	public static final String SYSADMIN_LOG_NOK = "NOK";
	
	public static final String SYSADMIN_ACTION_REPAIR = "Repair";
	public static final String SYSADMIN_ACTION_MANTEINANCE = "Manteinance";
	public static final String SYSADMIN_ACTION_PATCH = "Patch";
	public static final String SYSADMIN_ACTION_LOOKOUT = "Look_out"; 

}
