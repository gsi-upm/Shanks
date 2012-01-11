/** es.upm.dit.gsi.shanks
 20/12/2011

 */
package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * @author a.carrera
 *
 */
public abstract class ComplexScenario extends Scenario {

    private List<Scenario> scenarios;
    
    /**
     * Create a complex scenario to compose the whole scenario adding other scenarios. 
     * @param type
     */
    public ComplexScenario(String type) {
        super(type);
        this.scenarios = new ArrayList<Scenario>();
    }
    
    /**
     * Add the scenario to the complex scenario.
     * @param scenario
     */
    public void addScenario(Scenario scenario, GatewayDevice localDevice, GatewayDevice remoteDevice){
        //TOIMP crear clase GatewayDevice para conectar scenarios que se conectan a través de Link que puede ser extendido ilimitadas veces para tener diferentes conexiones
        scenarios.add(scenario);
        List<Device> devices = scenario.getDevices();
        for (Device device : devices) {
            this.currentDevices.add(device);
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
