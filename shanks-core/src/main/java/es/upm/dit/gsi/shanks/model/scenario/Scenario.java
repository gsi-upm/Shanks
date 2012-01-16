package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.failure.Failure;

/**
 * Scenarios class
 * 
 * This class create the different scenarios
 * 
 * @author Daniel Lara
 * @author a.carrera
 * @version 0.1.1
 * 
 */
public abstract class Scenario {

    Logger logger = Logger.getLogger(Scenario.class.getName());

    public String type;
    public List<NetworkElement> currentElements;
    public List<Failure> currentFailures;
    public List<Class<? extends Failure>> possibleFailures;

    /**
     * @param type
     */
    public Scenario(String type) {
        this.type = type;
        this.currentElements = new ArrayList<NetworkElement>();
        this.currentFailures = new ArrayList<Failure>();
        this.possibleFailures = new ArrayList<Class<? extends Failure>>();

        this.addNetworkElements();
        this.addPossibleFailures();
    }

    /**
     * @return
     */
    public String getName() {
        return type;
    }
    
    /**
     * @param element
     */
    public void addNetworkElement(NetworkElement element) {
        this.currentElements.add(element);
    }
    
    /**
     * @param element
     */
    public void removeNetworkElement(NetworkElement element) {
        this.currentElements.remove(element);
    }
    
    /**
     * @return
     */
    public List<NetworkElement> getCurrentElements() {
        return this.currentElements;
    }

    /**
     * 
     * 
     * @param failure Failure to add
     */
    public void addFailure(Failure failure) {
        if (this.possibleFailures.contains(failure.getClass())) {
            this.currentFailures.add(failure);
        } else {
            logger.warning("Failure was not added, because this scenario does not support this type of Failure. Failure type: "
                    + failure.getClass().getName());
        }
    }

    /**
     * @param failure
     */
    public void removeFailure(Failure failure) {
        this.currentFailures.remove(failure);
    }

    /**
     * @return
     */
    public List<Failure> getCurrentFailures() {
        return this.currentFailures;
    }

    /**
     * @param failure
     */
    public void addPossibleFailure(Class<? extends Failure> failure) {
        this.possibleFailures.add(failure);
    }

    /**
     * @param failureType
     */
    public void removePossibleFailure(Class<Failure> failureType) {
        this.possibleFailures.remove(failureType);
    }

    /**
     * @return
     */
    public List<Class<? extends Failure>> getPossibleFailures() {
        return this.possibleFailures;
    }

    /**
     * 
     */
    abstract public void addNetworkElements();

    /**
     * 
     */
    abstract public void addPossibleFailures();
}
