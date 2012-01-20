package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.ShanksSimulation2DGUI;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalID;

public class MyScenario2DPortrayal extends Scenario2DPortrayal {

    public MyScenario2DPortrayal(Scenario scenario, int width, int height) throws DuplicatedPortrayalID {
        super(scenario, width, height);
    }

    @Override
    public void placeElements() {
        
        this.situateDevice((Device)this.getScenario().getNetworkElement("D1"), 10, 50);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D2"), 50, 50);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D3"), 30, 30);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D4"), 10, 10);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D5"), 50, 10);
        
        this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
        
        
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
