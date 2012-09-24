package es.upm.dit.gsi.shanks.tutorial.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.tutorial.agent.action.ConnectComputerAction;
import es.upm.dit.gsi.shanks.tutorial.model.element.devices.Computer;

public class CyberManagerAgent extends SimpleShanksAgent{

	private static final long serialVersionUID = 7406514359512828818L;
	List<NetworkElement> el;
	List<NetworkElement> elToBeRepared;
	
	public CyberManagerAgent(String id) {
		super(id);
		this.el = new ArrayList<NetworkElement>();
		this.elToBeRepared = new ArrayList<NetworkElement>();
	}

	@Override
	public void checkMail() {
		
		
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation){
		ConnectComputerAction act = new ConnectComputerAction("Connect Computer", this);
		Scenario scen = simulation.getScenario();
		HashMap <String, NetworkElement> elements = scen.getCurrentElements();
		
		for(String s : elements.keySet()){
			if(elements.get(s).getStatus().containsKey(Computer.STATUS_DISCONNECTED)){
			el.add(elements.get(s));
			}
		}
		for(NetworkElement e : el){
			HashMap<String, Boolean> states = e.getStatus();
			if(states.get(Computer.STATUS_DISCONNECTED)){
				elToBeRepared.add(e);
				System.out.println("ELEMENTOS A REPARAR " + elToBeRepared.size());
			}		
		}
		try {
			act.executeAction(simulation, this, elToBeRepared);
		} catch (ShanksException e1) {
			e1.printStackTrace();
		}
		
	}

}
