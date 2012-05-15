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
	
	// Values for network elements properties. 
	public static final String DISCONNECTED = "disconnected";
	public static final String CONNECTED = "connected";
	public static final String ON = "on";
	public static final String OFF = "off";
	
	//whenever the value of an state or property is Not Applicable.
	public static final String NA = "not-aplicable";
	
	//
	public static final String NO_IP = "no-ip";
	
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
	
	// Enterprise net elements
	public static final String ENTERPRISE_GATEWAY_ID = "MegaCorpGateway";
	public static final String WEB_SERVER_ID = "MegaCorpWebServer"; 

}
