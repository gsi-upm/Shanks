package es.upm.dit.gsi.shanks.agent.action.test;

import jason.asSyntax.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.ShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.test.MyShanksAgent;
import es.upm.dit.gsi.shanks.exception.UnkownAgentException;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.link.LinkDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.link.OLTtoSplitter;

public class MyShanksAgentAction extends ShanksAgentAction {

    public static final String FIX = "fix";
    private Logger logger = Logger.getLogger(MyShanksAgent.class.getName());

    @Override
    public boolean executeAction(ShanksSimulation simulation, String agentID,
            List<Term> arguments) {

        Set<Failure> failures = simulation.getScenario().getCurrentFailures();
        int number = failures.size();
        int random = simulation.random.nextInt(number);
        Failure f = (Failure) failures.toArray()[random];
        HashMap<NetworkElement, String> elements = f.getAffectedElements();
        for (NetworkElement element : elements.keySet()) {
            Class<? extends NetworkElement> c = element.getClass();
            if (c.equals(OLT.class) || c.equals(ONT.class) || c.equals(Splitter.class)) {
                try {
                    element.setCurrentStatus(DeviceDefinitions.OK_STATUS);
                } catch (UnsupportedNetworkElementStatusException e) {
                    e.printStackTrace();
                }
            } else if (c.equals(OLTtoSplitter.class)) {
                try {
                    element.setCurrentStatus(LinkDefinitions.OK_STATUS);
                } catch (UnsupportedNetworkElementStatusException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            ((MyShanksAgent) simulation.getAgent(agentID))
                    .incrementNumberOfResolverFailures();
        } catch (UnkownAgentException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        // END OF THE ACTION
        simulation.getScenarioManager().checkFailures(simulation);
        logger.finer("Number of current failures: "
                + simulation.getScenario().getCurrentFailures().size());
        return true;
    }

}
