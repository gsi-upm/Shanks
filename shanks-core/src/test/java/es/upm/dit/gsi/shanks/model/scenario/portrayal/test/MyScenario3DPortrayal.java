package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class MyScenario3DPortrayal extends Scenario3DPortrayal {
    
    /**
     * @param scenario
     * @param width
     * @param height
     * @param length
     * @throws DuplicatedPortrayalIDException
     */
    public MyScenario3DPortrayal(Scenario scenario, int width, int height, int length) throws DuplicatedPortrayalIDException {
        super(scenario, width, height, length);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal#placeElements()
     */
    @Override
    public void placeElements() {
        
        this.situateDevice((Device)this.getScenario().getNetworkElement("D1"), 100.0, 500.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D2"), 500.0, 500.0, 500.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D3"), 300.0, 300.0, 300.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D4"), 100.0, 100.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("D5"), 500.0, 100.0, 500.0);
        
        this.drawLink((Link)this.getScenario().getNetworkElement("L1"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L2"));
        this.drawLink((Link)this.getScenario().getNetworkElement("L3"));
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
    @Override
    public void setupPortrayals() {
        ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice3DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink3DPortrayal());

        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        
    }

    @Override
    public void addPortrayals() {
        // TODO Auto-generated method stub
        
    }

}
