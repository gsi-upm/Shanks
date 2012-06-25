package es.upm.dit.gsi.shanks.model.scenario.portrayal.test;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.event.failiure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.util.test.FailureTest;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class MyScenario2DPortrayal extends Scenario2DPortrayal {

    /**
     * @param scenario
     * @param width
     * @param height
     * @throws DuplicatedPortrayalIDException
     */
    public MyScenario2DPortrayal(Scenario scenario, int width, int height) throws ShanksException {
        super(scenario, width, height);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
     */
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

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
    @Override
    public void setupPortrayals() {
        ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID).get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(MyDevice.class, new MyDevice2DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink2DPortrayal());
        failuresPortrayal.setPortrayalForClass(FailureTest.class, new Failure2DPortrayal());
    }

    @Override
    public void addPortrayals() {
        SparseGrid2D failuresGrid = new SparseGrid2D(100, 100);
        SparseGridPortrayal2D failuresPortrayal = new SparseGridPortrayal2D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID, MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
    }

}
