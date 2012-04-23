package es.upm.dit.gsi.shanks.agent.action.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.agent.action.SimpleShanksAgentAction;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class MyShanksAgentAction extends SimpleShanksAgentAction {

    public MyShanksAgentAction(String id, Steppable launcher) {
        super(id, launcher);
        // TODO Auto-generated constructor stub
    }

    public static final String FIX = "fix";
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
