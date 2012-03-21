package es.upm.dit.gsi.shanks.model.event.test;

import sim.engine.Steppable;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.event.Event;
import es.upm.dit.gsi.shanks.model.scenario.test.MyScenario;

public class MyEvent extends Event{

    public MyEvent(String name, Steppable generator) {
        super(name, generator);
    }

    @Override
    public void addChanges() {
        this.addAffectedPropertiesOfElement(MyDevice.TEMPERATURE_PROPERTY, 100);
        this.addAffectedPropertiesOfScenario(MyScenario.CLOUDY_PROB, "80");
    }
}
