/** es.upm.dit.gsi.shanks
 20/12/2011

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.failure.exception.NoCombinationForFailureException;
import es.upm.dit.gsi.shanks.model.failure.exception.UnsupportedElementInFailureException;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

/**
 * @author a.carrera
 * 
 */
public abstract class ComplexScenario extends Scenario {

    private HashMap<Scenario, List<Link>> scenarios;

    /**
     * Create a complex scenario to compose the whole scenario adding other
     * scenarios.
     * 
     * @param type
     * @param initialState
     * @param properties
     * @throws UnsupportedNetworkElementStatusException
     * @throws TooManyConnectionException
     * @throws UnsupportedScenarioStatusException
     * @throws DuplicatedIDException
     * @throws NonGatewayDeviceException
     * @throws AlreadyConnectedScenarioException
     */
    public ComplexScenario(String type, String initialState, Properties properties)
            throws UnsupportedNetworkElementStatusException,
            TooManyConnectionException, UnsupportedScenarioStatusException,
            DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException {
        super(type, initialState, properties);
        this.scenarios = new HashMap<Scenario, List<Link>>();
        
        this.addScenarios();
    }

    /**
     * Add all scenarios to the simulation using addScenario method
     * @throws DuplicatedIDException 
     * @throws UnsupportedScenarioStatusException 
     * @throws TooManyConnectionException 
     * @throws UnsupportedNetworkElementStatusException 
     * @throws AlreadyConnectedScenarioException 
     * @throws NonGatewayDeviceException 
     */
    abstract public void addScenarios() throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException, NonGatewayDeviceException, AlreadyConnectedScenarioException;

    /**
     * Add the scenario to the complex scenario.
     * 
     * @param scenario
     * @param gateway
     * @param externalLink
     * @throws NonGatewayDeviceException
     * @throws TooManyConnectionException
     * @throws DuplicatedIDException
     * @throws AlreadyConnectedScenarioException
     */
    public void addScenario(Scenario scenario, Device gateway, Link externalLink)
            throws NonGatewayDeviceException, TooManyConnectionException,
            DuplicatedIDException, AlreadyConnectedScenarioException {
        if (!scenario.getCurrentElements().containsKey(gateway.getID())) {
            throw new NonGatewayDeviceException(scenario, gateway, externalLink);
        } else {
            gateway.connectToLink(externalLink);
            if (!this.getCurrentElements().containsKey(externalLink.getID())) {
                this.addNetworkElement(externalLink);
            }
            if (!this.scenarios.containsKey(scenario)) {
                this.scenarios.put(scenario, new ArrayList<Link>());
            } else {
                List<Link> externalLinks = this.scenarios.get(scenario);
                if (!externalLinks.contains(externalLink)) {
                    externalLinks.add(externalLink);
                } else if (externalLink.getLinkedDevices().contains(gateway)) {
                    throw new AlreadyConnectedScenarioException(scenario,
                            gateway, externalLink);
                }
            }
        }
    }

    /**
     * @return
     */
    public Set<Scenario> getScenarios() {
        return this.scenarios.keySet();
    }
    
    /**
     * @param scenarioID
     * @return null if the ComplexScenario does not contains the searched scenarioID
     */
    public Scenario getScenario(String scenarioID) {
        for (Scenario s : this.scenarios.keySet()) {
            if (s.getID().equals(scenarioID)) {
                return s;
            }
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#generateFailures()
     */
    public void generateFailures() throws UnsupportedScenarioStatusException, NoCombinationForFailureException, UnsupportedElementInFailureException, InstantiationException, IllegalAccessException {
        super.generateFailures();
        for (Scenario scenario : this.scenarios.keySet()) {
            scenario.generateFailures();
        }
    }
    
    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#checkResolvedFailures()
     */
    public List<Failure> checkResolvedFailures() {
        List<Failure> fullList = new ArrayList<Failure>();
        
        fullList.addAll(super.checkResolvedFailures());
        
        for (Scenario scenario : this.scenarios.keySet()) {
           fullList.addAll(scenario.checkResolvedFailures());
        }
        
        return fullList;
    }

}
