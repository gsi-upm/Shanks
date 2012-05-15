package es.upm.dit.gsi.shanks.shanks_isp_module.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {

	public Values(ShanksSimulation sim) {
		super(sim);
		// TODO Auto-generated constructor stub
	}
	public static final String WIFI_ROUTER_ID = "WifiRouter#";
	public static final String ENTERPRISE_SCENARIO_ID = "MegaCorp";
	public static final String ENTERPRISE_SUSCRIBER_LINK_ID = "MegaCorpSuscription";
	
	
	// Simulation configuration
	public static final int NUMBER_OF_HANS = 8;
	public static final double HACKER_PROBABILITY = 0.5;

	
	

}
