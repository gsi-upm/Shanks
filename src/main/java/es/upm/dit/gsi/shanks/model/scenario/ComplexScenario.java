/** es.upm.dit.gsi.shanks
 20/12/2011

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;

/**
 * @author a.carrera
 *
 */
public abstract class ComplexScenario extends Scenario {

    private List<Scenario> scenarios;
    
    /**
     * Create a complex scenario to compose the whole scenario adding other scenarios. 
     * @param type
     * @throws TooManyConnectionException 
     * @throws UnsupportedNetworkElementStatusException 
     * @throws UnsupportedScenarioStatusException 
     * @throws DuplicatedIDException 
     */
    public ComplexScenario(String type, String initialState) throws UnsupportedNetworkElementStatusException, TooManyConnectionException, UnsupportedScenarioStatusException, DuplicatedIDException {
        super(type, initialState);
        this.scenarios = new ArrayList<Scenario>();
    }
    
    /**
     * Add the scenario to the complex scenario.
     * @param scenario
     */
    public void addScenario(Scenario scenario, Device localGateway, Link externalLink){
        //TOIMP crear clase GatewayDevice para conectar scenarios que se conectan a través de Link que puede ser extendido ilimitadas veces para tener diferentes conexiones
        this.scenarios.add(scenario);
        HashMap<String, NetworkElement> elements = scenario.getCurrentElements();
        for (String element : elements.keySet()) {
            this.currentElements.put(element, elements.get(element));
        }
        List<Class<? extends Failure>> possibleFailures = scenario.getPossibleFailures();
        for (Class<? extends Failure> possibleFailure : possibleFailures) {
            this.possibleFailures.add(possibleFailure);
        }
        List<Failure> failures = scenario.getCurrentFailures();
        for (Failure failure : failures) {
            this.currentFailures.add(failure);
        }
        // TODO hay que meter aquí el dispositivo que conecta los dos escenarios para crear el escenario complejo
    }
    
}
