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


	

}
