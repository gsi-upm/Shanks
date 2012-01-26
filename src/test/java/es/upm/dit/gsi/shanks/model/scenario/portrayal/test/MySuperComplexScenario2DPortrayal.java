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
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MySuperComplexScenario2DPortrayal extends
        ComplexScenario2DPortrayal {

    public MySuperComplexScenario2DPortrayal(Scenario scenario, int width,
            int height)
            throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height);
    }

    @Override
    public void placeScenarios() throws DuplicatedPortrayalID, ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("ComplexScenario1"), new Double2D(0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("ComplexScenario2"), new Double2D(0,160), ShanksMath.ANGLE_0, ShanksMath.ANGLE_180);
    }

    @Override
    public void placeElements() {       
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateDevice((Device)cs.getNetworkElement("SED1"), 100, 80);
        this.drawLink((Link)cs.getNetworkElement("SEL1"));
    }

    @Override
    public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice2DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink2DPortrayal());
    }

}
