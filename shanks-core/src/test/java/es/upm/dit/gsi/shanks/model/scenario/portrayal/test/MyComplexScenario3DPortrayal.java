package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyComplexScenario3DPortrayal extends ComplexScenario3DPortrayal {

    public MyComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length) throws DuplicatedPortrayalID, ScenarioNotFoundException {
        super(scenario, width, height, length);
    }

    @Override
    public void placeElements() {
        
        ComplexScenario cs = (ComplexScenario) this.getScenario();        
        this.situateDevice((Device)cs.getNetworkElement("ED1"), 0, 0, 0);
        this.drawLink((Link)cs.getNetworkElement("EL1"));
        this.drawLink((Link)cs.getNetworkElement("EL2"));
    }

    @Override
    public void setupPortrayals() {
        
        //Global
        ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice3DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.getDisplay(MAIN_DISPLAY_ID).setShowsAxes(false);

    }

    @Override
    public void placeScenarios() throws DuplicatedPortrayalID, ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("Scenario1"), new Double3D(0,0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("Scenario2"), new Double3D(0,0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_270, ShanksMath.ANGLE_180);
        
    }

}
