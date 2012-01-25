package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyComplexScenario2DPortrayal extends ComplexScenario2DPortrayal {

    public MyComplexScenario2DPortrayal(ComplexScenario scenario, int width, int height) throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height);
    }

    @Override
    public void placeElements() {
        
        ComplexScenario cs = (ComplexScenario) this.getScenario();       
        this.situateDevice((Device)cs.getNetworkElement("ED1"), 60, 60);        
        this.drawLink((Link)cs.getNetworkElement("EL1"));
        this.drawLink((Link)cs.getNetworkElement("EL2"));
        
    }

    @Override
    public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice2DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink2DPortrayal());
    }

    @Override
    public void placeScenarios() throws DuplicatedPortrayalID, ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("Scenario1"), new Double2D(0,0), ShanksMath.A0, ShanksMath.A0);
        this.situateScenario(cs.getScenario("Scenario2"), new Double2D(120,0), ShanksMath.A180, ShanksMath.A180);
        
    }
}
