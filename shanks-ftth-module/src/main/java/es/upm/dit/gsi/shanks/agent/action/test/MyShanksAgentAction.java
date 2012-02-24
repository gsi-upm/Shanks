package es.upm.dit.gsi.shanks.agent.action.test;

import jason.asSyntax.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.action.ShanksAgentAction;
import es.upm.dit.gsi.shanks.agent.MyShanksAgent;
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
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTEmitedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTReceivedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.ONTEmitedLaserFailure;
import es.upm.dit.gsi.shanks.model.ftth.failure.ONTReceivedLaserFailure;

public class MyShanksAgentAction extends ShanksAgentAction {

	public static final String FIX = "fix";
	private Logger logger = Logger.getLogger(MyShanksAgentAction.class
			.getName());

	@Override
	public boolean executeAction(ShanksSimulation simulation, String agentID,
			List<Term> arguments) {

		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		Set<Failure> failures = simulation.getScenario().getCurrentFailures();
		int number = failures.size();
		int random = simulation.random.nextInt(number);
		Failure f = (Failure) failures.toArray()[random];
		HashMap<NetworkElement, String> elements = f.getAffectedElements();

		boolean resolved = false;
		for (NetworkElement element : elements.keySet()) {
			if (f instanceof OLTReceivedLaserFailure) {
				Class<? extends NetworkElement> c = element.getClass();
				if (c.equals(OLT.class) || c.equals(ONT.class)
						|| c.equals(Splitter.class)) {
					try {
						element.changeProperty(OLT.receivedLaserPower, 50);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				} else if (c.equals(OLTtoSplitter.class)) {
					try {
						element.setCurrentStatus(LinkDefinitions.OK_STATUS);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				}
			} else if (f instanceof OLTEmitedLaserFailure) {
				Class<? extends NetworkElement> c = element.getClass();
				if (c.equals(OLT.class) || c.equals(ONT.class)
						|| c.equals(Splitter.class)) {
					try {
						element.changeProperty(OLT.emitedLaserPower, 50);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				} else if (c.equals(OLTtoSplitter.class)) {
					try {
						element.setCurrentStatus(LinkDefinitions.OK_STATUS);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				}
			} else if (f instanceof ONTEmitedLaserFailure) {
				Class<? extends NetworkElement> c = element.getClass();
				if (c.equals(OLT.class) || c.equals(ONT.class)
						|| c.equals(Splitter.class)) {
					try {
						element.changeProperty(ONT.emitedLaserPower, 50);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				} else if (c.equals(OLTtoSplitter.class)) {
					try {
						element.setCurrentStatus(LinkDefinitions.OK_STATUS);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				}
			} else if (f instanceof ONTReceivedLaserFailure) {
				Class<? extends NetworkElement> c = element.getClass();
				if (c.equals(OLT.class) || c.equals(ONT.class)
						|| c.equals(Splitter.class)) {
					try {
						element.changeProperty(ONT.receivedLaserPower, 50);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				} else if (c.equals(OLTtoSplitter.class)) {
					try {
						element.setCurrentStatus(LinkDefinitions.OK_STATUS);
						resolved=true;
					} catch (UnsupportedNetworkElementStatusException e) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			if (resolved) {
				((MyShanksAgent) simulation.getAgent(agentID))
						.incrementNumberOfResolverFailures();
			}
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
