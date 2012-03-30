package es.upm.dit.gsi.shanks.agent.action.test;

import jason.asSyntax.Term;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import sim.engine.Steppable;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.JasonShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.test.MyJasonShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.event.Action;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

public class MyShanksAgentAction extends SimpleShanksAgentAction {

    public MyShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    public static final String FIX = "fix";
    private Logger logger = Logger.getLogger(MyShanksAgentAction.class.getName());
   
    @Override
    public void addPossibleAffected() {
        this.addPossibleAffectedStatus(MyDevice.class, MyDevice.NOK_STATUS, false);
        this.addPossibleAffectedStatus(MyDevice.class, MyDevice.OK_STATUS, true);
        
    }
    @Override
    public void addAffectedScenario(Scenario scen) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void interactWithNE() {
        // TODO Auto-generated method stub
        
    }



}
