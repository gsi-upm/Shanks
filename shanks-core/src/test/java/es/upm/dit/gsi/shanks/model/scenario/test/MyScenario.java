package es.upm.dit.gsi.shanks.model.scenario.test;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

public class MyScenario extends Scenario {

    public MyScenario(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addNetworkElements() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addPossibleFailures() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPossibleStates() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) {
        // TODO Auto-generated method stub
        return null;
    }

}
