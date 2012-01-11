package es.upm.dit.gsi.shanks.model.scenario.ftth;

import es.upm.dit.gsi.shanks.model.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.element.device.ftth.Gateway;
import es.upm.dit.gsi.shanks.model.element.device.ftth.OLT;
import es.upm.dit.gsi.shanks.model.element.device.ftth.ONT;
import es.upm.dit.gsi.shanks.model.element.device.ftth.Splitter;
import es.upm.dit.gsi.shanks.model.failure.ftth.ONTFailure;
import es.upm.dit.gsi.shanks.model.scenario.ScenarioDefinitions;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;

//LOOK una clase como esta es la que cualquiera debe implementar para crear su propio escenario

public class ScenarioFTTH extends Scenario {

    public ScenarioFTTH() {
        super(ScenarioDefinitions.SCENARIO_FTTH_ACCESS_NETWORK);
    }

    @Override
    public void addDevices() {
        // TODO rellenar con los dispositivos de FTTH, por ejemplo:
        this.addDevice(new Gateway("Gateway de Juan", DeviceDefinitions.DEVICE_GATEWAY));
        this.addDevice(new OLT("OLT", DeviceDefinitions.DEVICE_OLT));
        this.addDevice(new ONT("ONT", DeviceDefinitions.DEVICE_ONT));
        this.addDevice(new Splitter("Splitter1", DeviceDefinitions.DEVICE_SPLITTER1));
        this.addDevice(new Splitter("Splitter2", DeviceDefinitions.DEVICE_SPLITTER2));
    }

    @Override
    public void addPossibleFailures() {
        // TODO rellenar los posibles tipos de failures, por ejemplo:
        this.addPossibleFailure(ONTFailure.class);
    }
    
    // TODO hay que añadir la manera de situarlos "en el espacio", ahora mismo eso esta hecho en Simulation.java

}
