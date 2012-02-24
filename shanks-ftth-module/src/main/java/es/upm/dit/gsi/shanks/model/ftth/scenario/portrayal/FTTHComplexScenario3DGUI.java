package es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.util.Double3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.GatewayRouter;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.GatewayRouter.GatewayRouter3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.OLT.OLT3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.ONT.ONT3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.Splitter.Splitter3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.link.portrayal.OLTtoSplitter3DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTFailure;
import es.upm.dit.gsi.shanks.model.ftth.scenario.ScenarioDefinitions;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;


public class FTTHComplexScenario3DGUI extends ComplexScenario3DPortrayal{

	
	 /**
     * @param scenario
     * @param width
     * @param height
     * @param length
     * @throws DuplicatedPortrayalIDException
     * @throws ScenarioNotFoundException
     */
	public FTTHComplexScenario3DGUI(Scenario scenario, long width, long height,
			long length) throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		super(scenario, width, height, length);
		}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario3DPortrayal#placeScenarios()
     */
	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
		ComplexScenario cs = (ComplexScenario) this.getScenario();
        this.situateScenario(cs.getScenario("HomeScenario"), new Double3D(-200,0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        this.situateScenario(cs.getScenario("OfficeScenario"), new Double3D(200,0,0), ShanksMath.ANGLE_0, ShanksMath.ANGLE_270, ShanksMath.ANGLE_180);
        
		
	}

	@Override
	public void addPortrayals() {
		Continuous3D failuresGrid = new Continuous3D(5, 100, 100, 100);
        ContinuousPortrayal3D failuresPortrayal = new ContinuousPortrayal3D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(ScenarioDefinitions.FAILURE_DISPLAY_ID,ScenarioDefinitions.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal#placeElements()
     */
	@Override
	public void placeElements() {
		ComplexScenario cs = (ComplexScenario) this.getScenario();        
        this.situateDevice((Device)cs.getNetworkElement("Cloud"), 0, 0, 0);
        this.drawLink((Link)cs.getNetworkElement("HomeLink"));
        this.drawLink((Link)cs.getNetworkElement("OfficeLink"));
		
	}

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
	@Override
	public void setupPortrayals() {
		ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(ScenarioDefinitions.FAILURE_DISPLAY_ID).get(ScenarioDefinitions.FAILURE_PORTRAYAL_ID);
//        devicePortrayal.setPortrayalForAll(new OLT3DPortrayal());
        devicePortrayal.setPortrayalForClass(OLT.class, new OLT3DPortrayal());
        devicePortrayal.setPortrayalForClass(ONT.class, new ONT3DPortrayal());
        devicePortrayal.setPortrayalForClass(Splitter.class, new Splitter3DPortrayal());
        devicePortrayal.setPortrayalForClass(GatewayRouter.class, new GatewayRouter3DPortrayal());
        networkPortrayal.setPortrayalForAll(new OLTtoSplitter3DPortrayal());
        failuresPortrayal.setPortrayalForClass(OLTFailure.class, new Failure3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.getDisplay(MAIN_DISPLAY_ID).setShowsAxes(true);

		
	}

}
