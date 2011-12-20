package es.upm.dit.gsi.shanks.model.scenario.ftth;

import es.upm.dit.gsi.shanks.model.common.Definitions;
import es.upm.dit.gsi.shanks.model.failure.ftth.ONTFailure;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.ftth.device.Gateway;
import es.upm.dit.gsi.shanks.model.scenario.ftth.device.OLT;
import es.upm.dit.gsi.shanks.model.scenario.ftth.device.ONT;
import es.upm.dit.gsi.shanks.model.scenario.ftth.device.Splitter;

//LOOK una clase como esta es la que cualquiera debe implementar para crear su propio escenario

public class ScenarioFTTH extends Scenario {

    public ScenarioFTTH() {
        super(Definitions.SCENARIO_FTTH);
    }

    @Override
    public void addDevices() {
        // TODO rellenar con los dispositivos de FTTH, por ejemplo:
        this.addDevice(new Gateway("Gateway", 0, 45, Definitions.GATEWAY));
        this.addDevice(new OLT("OLT", 0, 45, Definitions.OLT));
        this.addDevice(new ONT("ONT", 0, 45, Definitions.ONT));
        this.addDevice(new Splitter("Splitter1", 0, 45, Definitions.SPLITTER1));
        this.addDevice(new Splitter("Splitter2", 0, 45, Definitions.SPLITTER2));
    }

    @Override
    public void addPossibleFailures() {
        // TODO rellenar los posibles tipos de failures, por ejemplo:
        this.addPossibleFailure(ONTFailure.class);
    }

}
