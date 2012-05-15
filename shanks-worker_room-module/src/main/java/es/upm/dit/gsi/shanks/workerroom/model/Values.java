package es.upm.dit.gsi.shanks.workerroom.model;

import es.upm.dit.gsi.shanks.ShanksSimulation;

public class Values extends es.upm.dit.gsi.shanks.networkattacks.util.Values{

	
	public Values(ShanksSimulation sim) {
		super(sim);
		// TODO Auto-generated constructor stub
	}
	
	public static final String WR_COMPUTER_ID = "WorkerRoomTerminal#";
	public static final String WR_ETHERNET_ID = "WorkerRoomLink#";
	public static final String WR_Printer_ID = "WorkerRoomPrinter";
	
	// Agents Values. 
	public static final String REPAIR_WIRE_ACTION_ID = "Repair Wire";
	public static final Object REPAIR_WIRE_PROB = 0.5;
	public static final int NUMBER_OF_WORKERROOM_PCS = 5;
	


}
