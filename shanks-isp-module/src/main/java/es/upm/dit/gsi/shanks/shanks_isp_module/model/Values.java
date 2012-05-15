package es.upm.dit.gsi.shanks.shanks_isp_module.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {

	public Values(ShanksSimulation sim) {
		super(sim);
	}
	
	// HANS values. 
	public static final String HAN_SUSCRIBER_LINK = "HanSuscription";
	public static final String HAN_SCENARIO_ID = "HomeAreaNetwork#";
	
	// Enterpirse Values
	public static final String ENTERPRISE_SCENARIO_ID = "MegaCorp";
	public static final String ENTERPRISE_SUSCRIBER_LINK_ID = "MegaCorpSuscription";

}
