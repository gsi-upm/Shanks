package es.upm.dit.gsi.shanks.model.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.upm.dit.gsi.shanks.model.device.Device;
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
    public List<Device> currentDevices;
    public List<Failure> currentFailures;
    public List<Class<? extends Failure>> possibleFailures;

    public Scenario(String type) {
        this.type = type;
        this.currentDevices = new ArrayList<Device>();
        this.currentFailures = new ArrayList<Failure>();
        this.possibleFailures = new ArrayList<Class<? extends Failure>>();

        this.addDevices();
        this.addPossibleFailures();
    }

    public String getName() {
        return type;
    }

    public List<Device> getDevices() {
        return currentDevices;
    }

    public void addDevice(Device device) {
        this.currentDevices.add(device);
    }

    public void removeDevice(Device device) {
        this.currentDevices.remove(device);
    }

    public List<Device> getCurrentDevices() {
        return this.currentDevices;
    }

    public void addFailure(Failure failure) {
        if (this.possibleFailures.contains(failure.getClass())) {
            this.currentFailures.add(failure);
        } else {
            logger.warning("Failure was not added, because this scenario does not support this type of Failure. Failure type: "
                    + failure.getClass().getName());
        }
    }

    public void removeFailure(Failure failure) {
        this.currentFailures.remove(failure);
    }

    public List<Failure> getCurrentFailures() {
        return this.currentFailures;
    }

    public void addPossibleFailure(Class<? extends Failure> failure) {
        this.possibleFailures.add(failure);
    }

    public void removePossibleFailure(Class<Failure> failureType) {
        this.possibleFailures.remove(failureType);
    }

    public List<Class<? extends Failure>> getPossibleFailures() {
        return this.possibleFailures;
    }

    abstract public void addDevices();

    abstract public void addPossibleFailures();
}
