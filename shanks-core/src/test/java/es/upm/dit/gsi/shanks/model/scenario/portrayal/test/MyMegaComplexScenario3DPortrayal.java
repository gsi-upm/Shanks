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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

/**
 * @author a.carrera
 *
 */
public class MyMegaComplexScenario3DPortrayal extends
        ComplexScenario3DPortrayal {

    /**
     * @param scenario
     * @param width
     * @param height
     * @param length
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
    public MyMegaComplexScenario3DPortrayal(Scenario scenario, long width,
            long height, long length) throws DuplicatedPortrayalIDException,
            ScenarioNotFoundException {
        super(scenario, width, height, length);
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal#placeElements()
     */
    @Override
    public void placeElements() {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateDevice((Device) cs.getNetworkElement("MED1"), -150, 500, -150);
        this.drawLink((Link) cs.getNetworkElement("MEL1"));
    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
    @Override
    public void setupPortrayals() {

        // Global
        ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this
                .getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                .get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this
                .getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID)
                .get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicePortrayal.setPortrayalForClass(MyDevice.class,
                new MyDevice3DPortrayal());
        networkPortrayal.setPortrayalForAll(new MyLink3DPortrayal());

        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
//        this.getDisplay(MAIN_DISPLAY_ID).setShowsAxes(false);

    }

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario3DPortrayal#placeScenarios()
     */
    @Override
    public void placeScenarios() throws DuplicatedPortrayalIDException,
            ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("SuperComplexScenario1"),
                new Double3D(0, 0, 0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0,
                ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("SuperComplexScenario2"),
                new Double3D(-300, 0, -300), ShanksMath.ANGLE_0, ShanksMath.ANGLE_180,
                ShanksMath.ANGLE_0);
    }

    @Override
    public void addPortrayals() {
        // TODO Auto-generated method stub
        
    }

}
