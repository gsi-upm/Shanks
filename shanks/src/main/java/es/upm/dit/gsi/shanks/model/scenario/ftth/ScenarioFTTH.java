package es.upm.dit.gsi.shanks.model.scenario.ftth;

import es.upm.dit.gsi.shanks.model.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.device.ftth.Gateway;
import es.upm.dit.gsi.shanks.model.device.ftth.OLT;
import es.upm.dit.gsi.shanks.model.device.ftth.ONT;
import es.upm.dit.gsi.shanks.model.device.ftth.Splitter;
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
        this.addDevice(new Gateway("Gateway de Juan", 0, 45));
        this.addDevice(new OLT("OLT", 0, 45, ScenarioDefinitions.OLT));
        this.addDevice(new ONT("ONT", 0, 45, ScenarioDefinitions.ONT));
        this.addDevice(new Splitter("Splitter1", 0, 45, ScenarioDefinitions.SPLITTER1));
        this.addDevice(new Splitter("Splitter2", 0, 45, ScenarioDefinitions.SPLITTER2));
    }

    @Override
    public void addPossibleFailures() {
        // TODO rellenar los posibles tipos de failures, por ejemplo:
        this.addPossibleFailure(ONTFailure.class);
    }

}
