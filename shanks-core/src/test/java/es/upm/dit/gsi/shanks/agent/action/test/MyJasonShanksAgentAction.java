package es.upm.dit.gsi.shanks.agent.action.test;

import jason.asSyntax.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.agent.action.JasonShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.test.MyJasonShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyJasonShanksAgentAction extends JasonShanksAgentAction {

    public static final String FIX = "fix";
    private Logger logger = Logger
            .getLogger(MyJasonShanksAgent.class.getName());

    public boolean executeAction(ShanksSimulation simulation,
            ShanksAgent agent, List<Term> arguments) {

        Set<Failure> failures = simulation.getScenario().getCurrentFailures();
        int number = failures.size();
        int random = simulation.random.nextInt(number);
        Failure f = (Failure) failures.toArray()[random];
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

        ((MyJasonShanksAgent) agent).incrementNumberOfResolverFailures();
        // END OF THE ACTION
        simulation.getScenarioManager().checkFailures(simulation);
        logger.finer("Number of current failures: "
                + simulation.getScenario().getCurrentFailures().size());
        return true;
    }

}
