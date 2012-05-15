package es.upm.dit.gsi.shanks.shanks_enterprise_module.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values {

	// Worker_Room Values
	public static final String WORKER_ROOM_SCENARIO_ID = "WorkerRoom#";
	public static final String WORKER_ROOM_LINK_ID = "WorkerRoomExternalLink";
	
	// Datacenter values
	public static final String DATA_CENTER_SCENARIO_ID = "DataCenter";
	public static final String DATA_CENTER_LINK_ID = "DataCenterExternalLink";
	

	public Values(ShanksSimulation sim) {
		super(sim);
	}

}
