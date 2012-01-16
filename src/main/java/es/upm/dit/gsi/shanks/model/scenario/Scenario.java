package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ec.util.MersenneTwisterFast;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
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

    public String id;
    private List<String> possibleStates;
    private String currentStatus;
    public List<NetworkElement> currentElements;
    public List<Failure> currentFailures;
    public List<Class<? extends Failure>> possibleFailures;

    /**
     * @param type
     */
    public Scenario(String id) {
        this.id = id;
        this.currentElements = new ArrayList<NetworkElement>();
        this.currentFailures = new ArrayList<Failure>();
        this.possibleFailures = new ArrayList<Class<? extends Failure>>();

        this.setPossibleStates();
        this.addNetworkElements();
        this.addPossibleFailures();
    }

    /**
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * @return the currentStatus
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @param desiredStatus
     *            the currentStatus to set
     * @return true if the status was set correctly and false if the status is
     *         not a possible status of the network element
     * @throws UnsupportedNetworkElementStatusException
     */
    public boolean setCurrentStatus(String desiredStatus)
            throws UnsupportedNetworkElementStatusException {
        if (this.isPossibleStatus(desiredStatus)) {
            this.currentStatus = desiredStatus;
            return true;
        } else {
            logger.warning("Impossible to set status: " + desiredStatus
                    + ". This network element " + this.getID()
                    + "does not support this status.");
            throw new UnsupportedNetworkElementStatusException();
        }
    }

    /**
     * @param possibleStatus
     * @return
     */
    private boolean isPossibleStatus(String possibleStatus) {
        if (this.possibleStates.contains(possibleStatus)) {
            return true;
        } else {
            return false;
        }
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
     * @param failure
     *            Failure to add
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
    abstract public void setPossibleStates();

    /**
     * 
     */
    abstract public void addNetworkElements();

    /**
     * 
     */
    abstract public void addPossibleFailures();

    /**
     * 
     */
    public void generateFailures() {
        MersenneTwisterFast randomizer = new MersenneTwisterFast();
        String status = this.getCurrentStatus();
        HashMap<Class<? extends Failure>, Double> penalties = this
                .getPenaltiesInStatus(status);
        Iterator<Class<? extends Failure>> it = this.getPossibleFailures()
                .iterator();
        while (it.hasNext()) {
            Class<? extends Failure> type = it.next();
            double penalty = 0;
            double prob = 0;
            try {
                Failure failure = type.newInstance();
                try {
                    penalty = penalties.get(type);
                    prob = failure.getOccurrenceProbability() * penalty;
                } catch (Exception e) {
                    logger.fine("There is no penalty for failures: "
                            + type.getName() + " in status " + status);
                }
                if (randomizer.nextDouble()<prob) {
                    failure.activateFailure();
                    this.addFailure(failure);
                }
            } catch (Exception e) {
                logger.severe("Impossible to instance failure: " + type.getName() + ". All failures must have a default constructor that calls other constructor Failure(String id, double occurrenceProbability)");
                logger.severe("Exception: " + e.getMessage());
            }
        }
    }

    /**
     * @param status
     * @return Multiplier for each type of failure
     */
    abstract public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status);

    /**
     * @return resolved failures
     */
    public List<Failure> checkResolvedFailures() {
        List<Failure> resolvedFailures = new ArrayList<Failure>();
       for (Failure failure : this.currentFailures) {
           if (failure.isResolved()) {
               resolvedFailures.add(failure);
           }
       }
       for (Failure resolved : resolvedFailures) {
           this.currentFailures.remove(resolved);
       }
       return resolvedFailures;
    }

}
