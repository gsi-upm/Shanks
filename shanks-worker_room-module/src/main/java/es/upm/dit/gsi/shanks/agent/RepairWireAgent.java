package es.upm.dit.gsi.shanks.agent;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.RepairWire;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.workerroom.element.link.EthernetLink;

public class RepairWireAgent extends SimpleShanksAgent{

	public RepairWireAgent(String id) {
		super(id);
	}

	public void checkMail() {
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		RepairWire repair = new RepairWire("Repair Wire", this);
		List <NetworkElement> ne = new ArrayList<NetworkElement>();
		List<NetworkElement> linksDamaged = new ArrayList<NetworkElement>();
//		ComplexScenario comp = (ComplexScenario) simulation.getScenario();
//		try {
//			System.out.println("CURRENT FAILURES " + comp.getScenario("Worker Room 1").getCurrentFailures().size());
//		} catch (ScenarioNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}

		for(String s : simulation.getScenario().getCurrentElements().keySet()){
            ne.add(simulation.getScenario().getCurrentElements().get(s));
        }
        for(NetworkElement e : ne){
        		if(e.getProperties().containsKey(EthernetLink.PROPERTY_PACKETLOSSRATIO)){
        		if((Double) e.getProperties().get(EthernetLink.PROPERTY_PACKETLOSSRATIO) > 0.1){
        			linksDamaged.add(e);
        			if(linksDamaged.size() > 3){
        			try {
						repair.executeAction(simulation, this, linksDamaged);
					} catch (UnsupportedNetworkElementStatusException e1) {
						e1.printStackTrace();
					} catch (UnsupportedScenarioStatusException e1) {
						e1.printStackTrace();
					}
        		
        			}
        		}
        		try {
    				e.checkStatus();
                	} catch (UnsupportedNetworkElementStatusException e1) {
    				e1.printStackTrace();
    				}
        		}
        		
        }
	}
	
	private static final long serialVersionUID = 8549991416825517733L;

}
