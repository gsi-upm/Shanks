package es.upm.dit.gsi.shanks.hackerhan.model;

public class Values {
	
	// typical values for device properties. 
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
	
	// Network Elements names. 
	public static final String COMPUTER_ID = "Computer#";
	public static final String WIFI_ROUTER_ID = "WifiRouter#";
	public static final String ANDROID_ID = "Android#";
	public static final String TABLET_ID = "Tablet#";
	public static final String ETHERNET_ID = "Ethernet#";
	public static final String WIFI_ID = "Wifi#";
	
	// Scenario Values. 
	public static final double ETHERNET_LENGHT = 2.5;
	public static final int WIFI_CHANNELS = 64;
	
	// Hacker status
	public static final String ACTION_NONE = "NONE";
	public static final String ACTION_GET_BOT = "GET_BOT";
	public static final String ACTION_PROXY_ATTACK = "PROXY_ATTACK";
	public static final String ACTION_DIRECT_ATTACK = "DIRECT_ATTACK";

	public static final String ATTACK_NONE = "NONE";
	public static final String ATTACK_DDOS = "DDOS";
	public static final String ATTACK_ROOT_SHELL = "ROOT_SHELL";
	public static final String ATTACK_SQL_INJECTION = "SQL_INJECTION";
	
	public static final String ATTACK_ORDER = "StartAttack";
	
}
