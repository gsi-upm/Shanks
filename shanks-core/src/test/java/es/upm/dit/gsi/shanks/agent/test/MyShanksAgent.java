package es.upm.dit.gsi.shanks.agent.test;

import jason.asSemantics.ActionExec;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.upm.dit.gsi.shanks.agent.ShanksAgent;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.test.MyLink;
import es.upm.dit.gsi.shanks.model.failure.Failure;

public class MyShanksAgent extends ShanksAgent {

    /**
     * @param id
     * @param aslFilePath
     */
    public MyShanksAgent(String id, String aslFilePath) {
        super(id, aslFilePath);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 2187089758395179991L;

    @Override
    public List<Literal> perceive() {
        if (!isRunning())
            return null;
        if (this.getSimulation() == null)
            return null;
        
        List<Literal> percepts = new ArrayList<Literal>();
        percepts.add(ASSyntax.createLiteral("step", new Term[] { ASSyntax.createNumber(1) }));
        percepts.add(ASSyntax.createLiteral("repair", new Term[] { ASSyntax.createNumber(1) }));
        return percepts;
    }

    @Override
    public void act(ActionExec action, List<ActionExec> feedback) {
        if (!isRunning())
            return;
        if (this.getSimulation() == null)
            return;
        // TOIMP ShanksAction class
        // TODO add ShanksAction as pending actions

        // THIS SHOULD BE INSIDE THE ACTIONEXEC
        Set<Failure> failures = this.getSimulation().getScenario()
                .getCurrentFailures();
        for (Failure f : failures) {
            HashMap<NetworkElement, String> elements = f.getAffectedElements();
            for (NetworkElement element : elements.keySet()) {
                Class<? extends NetworkElement> c = element.getClass();
                if (c.equals(MyDevice.class)) {
                    try {
                        element.setCurrentStatus(MyDevice.OK_STATUS);
                    } catch (UnsupportedNetworkElementStatusException e) {
                        action.setResult(false);
                        e.printStackTrace();
                    }
                } else if (c.equals(MyLink.class)) {
                    try {
                        element.setCurrentStatus(MyLink.OK_STATUS);
                    } catch (UnsupportedNetworkElementStatusException e) {
                        action.setResult(false);
                        e.printStackTrace();
                    }
                }
            }
            // Resolve only 1 failure
            break;
        }
        // END OF THE ACTION

        action.setResult(true);

    }

}
