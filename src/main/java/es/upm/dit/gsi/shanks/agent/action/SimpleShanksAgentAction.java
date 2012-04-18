package es.upm.dit.gsi.shanks.agent.action;

import java.util.ArrayList;
import java.util.List;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.event.Action;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public abstract class SimpleShanksAgentAction extends Action{

    public List<NetworkElement> affected;
    
    public SimpleShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);
        this.affected = new ArrayList<NetworkElement>();
        
    }
    
    public void executeAction(ShanksSimulation simulation, ShanksAgent agent, List<NetworkElement> arguments) throws UnsupportedNetworkElementStatusException, UnsupportedScenarioStatusException {
        for(NetworkElement ne : arguments){
            this.addAffectedElement(ne);
            
        }
        this.launchEvent(); 

    }

    
    
}
