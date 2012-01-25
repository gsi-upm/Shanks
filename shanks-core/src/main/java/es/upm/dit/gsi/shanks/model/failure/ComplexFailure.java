/** es.upm.dit.gsi.shanks
 11/01/2012

 */
package es.upm.dit.gsi.shanks.model.failure;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

/**
 * @author a.carrera
 * 
 */

//TODO I think complex failure is not required... because you can add any number of devices to a simple failure
// with no dependency of the scenario
public abstract class ComplexFailure extends Failure {

    private HashMap<Scenario, HashMap<Class<? extends Failure>, List<Set<NetworkElement>>>> addedFailuresByScenario;

    /**
     * @param type
     */
    public ComplexFailure(String id, double occurrenceProbability) {
        super(id, occurrenceProbability);
        this.addedFailuresByScenario = new HashMap<Scenario, HashMap<Class<? extends Failure>, List<Set<NetworkElement>>>>();
        this.addPossibleActivedFailures();
    }

    private void addPossibleActivedFailures() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Add a failure to the complex failure
     * 
     * @param scenario
     * @param possibleFailure
     * @param failureConfigurations
     */
    public void addPossibleFailure(Scenario scenario,
            Class<? extends Failure> possibleFailure,
            List<Set<NetworkElement>> failureConfigurations) {
        if (!this.addedFailuresByScenario.containsKey(scenario)) {
            this.addedFailuresByScenario
                    .put(scenario,
                            new HashMap<Class<? extends Failure>, List<Set<NetworkElement>>>());
        }
        this.addedFailuresByScenario.get(scenario).put(possibleFailure,failureConfigurations);
    }

}
