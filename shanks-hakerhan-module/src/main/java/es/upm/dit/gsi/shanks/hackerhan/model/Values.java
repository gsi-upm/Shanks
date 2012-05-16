package es.upm.dit.gsi.shanks.hackerhan.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values  {
	
	public Values(ShanksSimulation sim) {
		super(sim);
	}

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
	public static final Double WIRELESSD_FAILURE_PROB = 0.01;
	
	// Network Elements names. 
	public static final String COMPUTER_ID = "Computer";
	public static final String ANDROID_ID = "Android";
	public static final String TABLET_ID = "Tablet";
	public static final String ETHERNET_ID = "Ethernet";
	public static final String WIFI_ID = "Wifi";
	
	// Scenario Values. 
	public static final double ETHERNET_LENGHT = 2.5;
	public static final int WIFI_CHANNELS = 64;
	
	// Hacker status
	public static final String ACTION_NONE = "Nada";
	public static final String ACTION_GET_BOT = "Conseguir_Bot";
	public static final String ACTION_PROXY_ATTACK = "Ataque_Con_Proxy";
	public static final String ACTION_DIRECT_ATTACK = "Ataque_A_Pelo";

	public static final String ATTACK_NONE = "Nada";
	public static final String ATTACK_DDOS = "DDoS";
	public static final String ATTACK_ROOT_SHELL = "RootShell";
	public static final String ATTACK_SQL_INJECTION = "SQL_injection";
	
	public static final String ATTACK_ORDER = "StartAttack";
	public static final String STOP_ORDER = "StopAttack";
	public static final int ATTACK_MAX_STEPS = 10;
	
	public static final int LOAD_INCREASE = 5;
	public static final int MAX_ABILITY = 10;
}
