package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyComplexScenario2DPortrayal extends Scenario2DPortrayal {

    public MyComplexScenario2DPortrayal(ComplexScenario scenario, int width, int height) throws DuplicatedPortrayalID {
        super(scenario, width, height);
    }

    @Override
    public void placeElements() {
        
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        
        Scenario s1 = cs.getScenario("Scenario1");
        this.situateDevice((Device)s1.getNetworkElement("D1"), 10, 50);
        this.situateDevice((Device)s1.getNetworkElement("D2"), 50, 50);
        this.situateDevice((Device)s1.getNetworkElement("D3"), 30, 30);
        this.situateDevice((Device)s1.getNetworkElement("D4"), 10, 10);
        this.situateDevice((Device)s1.getNetworkElement("D5"), 50, 10);
        
        this.drawLink((Link)s1.getNetworkElement("L1"));
        this.drawLink((Link)s1.getNetworkElement("L2"));
        this.drawLink((Link)s1.getNetworkElement("L3"));
        
        Scenario s2 = cs.getScenario("Scenario2");
        this.situateDevice((Device)s2.getNetworkElement("D1"), 70, 50);
        this.situateDevice((Device)s2.getNetworkElement("D2"), 70, 30);
        this.situateDevice((Device)s2.getNetworkElement("D3"), 60, 10);
        this.situateDevice((Device)s2.getNetworkElement("D4"), 80, 50);
        this.situateDevice((Device)s2.getNetworkElement("D5"), 60, 30);
        
        this.drawLink((Link)s2.getNetworkElement("L1"));
        this.drawLink((Link)s2.getNetworkElement("L2"));
        this.drawLink((Link)s2.getNetworkElement("L3"));
        
        this.drawLink((Link)cs.getNetworkElement("EL1"));
        
    }

    @Override
    public void setupPortrayals() {
        SparseGridPortrayal2D devicePortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(ShanksSimulation2DGUI.MAIN_DISPLAY).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(ShanksSimulation2DGUI.MAIN_DISPLAY).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice2DPortrayal());
//        networkPortrayal.setPortrayalForClass(MyLink.class, new MyLink2DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink2DPortrayal());
        //TODO echarle un ojo a esto para que pueda pintarse cada link como se quiera
    }
}
