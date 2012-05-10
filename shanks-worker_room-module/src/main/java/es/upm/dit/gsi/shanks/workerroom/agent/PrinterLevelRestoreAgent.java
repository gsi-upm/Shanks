package es.upm.dit.gsi.shanks.workerroom.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;

public class PrinterLevelRestoreAgent extends SimpleShanksAgent{

	private Logger logger = Logger.getLogger(PrinterLevelRestoreAgent.class.getName());

	public PrinterLevelRestoreAgent(String id) {
		super(id);
		
	}

	public void checkMail() {

	}

	@Override
	public void executeReasoningCycle(ShanksSimulation sim) {
		HashMap<String, NetworkElement> allDevices = sim.getScenario().getCurrentElements();
		List<Printer> printers = new ArrayList<Printer>();
		for(String s : allDevices.keySet()){
			if(allDevices.get(s) instanceof Printer){
				printers.add((Printer) allDevices.get(s));	
			}
		}
		for(Printer p : printers){
			if(((Double)p.getProperty(Printer.PROPERTY_INK)) < 0.10){
				try {
					p.setInkLevel(1.0);
				} catch (UnsupportedNetworkElementStatusException e) {
					logger.severe("Cannot restore the normal ink level");
				}
			}
			if(((Double)p.getProperty(Printer.PROPERTY_PAPER)) < 25){
				try {
					p.setPaperLevel(500);
				} catch (UnsupportedNetworkElementStatusException e) {
					logger.severe("Cannot restore the normal paper level");
				}
			}
		}
	}
	
	private static final long serialVersionUID = -238172229380328568L;

}
