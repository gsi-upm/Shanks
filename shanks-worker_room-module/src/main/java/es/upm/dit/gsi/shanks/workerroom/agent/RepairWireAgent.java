package es.upm.dit.gsi.shanks.workerroom.agent;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.workerroom.agent.action.RepairWire;
import es.upm.dit.gsi.shanks.workerroom.model.Values;
import es.upm.dit.gsi.shanks.workerroom.model.element.link.EthernetLink;

public class RepairWireAgent extends SimpleShanksAgent{

	public RepairWireAgent(String id) {
		super(id);
	}

	public void checkMail() {
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		RepairWire repair = new RepairWire(Values.REPAIR_WIRE_ACTION_ID, this);
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
					} catch (UnsupportedNetworkElementFieldException e1) {
						e1.printStackTrace();
					} catch (UnsupportedScenarioStatusException e1) {
						e1.printStackTrace();
					}
        		
        			}
        		}
        		try {
    				e.checkStatus();
                	} catch (UnsupportedNetworkElementFieldException e1) {
    				e1.printStackTrace();
    				}
        		}
        		
        }
	}
	
	private static final long serialVersionUID = 8549991416825517733L;

}
