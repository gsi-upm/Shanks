package es.upm.dit.gsi.shanks.agent.action.test;

import java.util.HashMap;
import java.util.Set;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.ShanksAgentAction;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyShanksAgentAction extends ShanksAgentAction {

    @Override
    public boolean executeAction(ShanksSimulation simulation) {
        
        Set<Failure> failures = simulation.getScenario()
                .getCurrentFailures();
        for (Failure f : failures) {
            HashMap<NetworkElement, String> elements = f.getAffectedElements();
            for (NetworkElement element : elements.keySet()) {
                Class<? extends NetworkElement> c = element.getClass();
                if (c.equals(MyDevice.class)) {
                    try {
                        element.setCurrentStatus(MyDevice.OK_STATUS);
                    } catch (UnsupportedNetworkElementStatusException e) {
                        e.printStackTrace();
                    }
                } else if (c.equals(MyLink.class)) {
                    try {
                        element.setCurrentStatus(MyLink.OK_STATUS);
                    } catch (UnsupportedNetworkElementStatusException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Resolve only 1 failure
            break;
        }
        // END OF THE ACTION
return true;
    }

}
