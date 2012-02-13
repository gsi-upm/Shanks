package es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
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
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class OfficeScenario3DPortrayal extends Scenario3DPortrayal{

	public OfficeScenario3DPortrayal(Scenario scenario, long width,
			long height, long length) throws DuplicatedPortrayalIDException {
		super(scenario, width, height, length);
		
	}

	@Override
	public void addPortrayals() {
		Continuous3D failuresGrid = new Continuous3D(5, 100, 100, 100);
        ContinuousPortrayal3D failuresPortrayal = new ContinuousPortrayal3D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(ScenarioDefinitions.FAILURE_DISPLAY_ID, ScenarioDefinitions.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
		
	}

	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("OLT"), 200, 1000, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter1"), 500, 500, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter2"), 500, 1000, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter3"), 500, 1500, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT1"), 1000, 500, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT2"), 1000, 1000, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT3"), 1000, 1500, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router1"), 1500, 250, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router2"), 1500, 500, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router3"), 1500, 750, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router4"), 1500, 1000, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router5"), 1500, 1250, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router6"), 1500, 1500, 0);
		
		this.drawLink((Link)this.getScenario().getNetworkElement("Link0"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link3"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link4"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link5"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link6"));
		
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(ScenarioDefinitions.FAILURE_DISPLAY_ID).get(ScenarioDefinitions.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(OLT.class, new OLT3DPortrayal());
        devicePortrayal.setPortrayalForClass(ONT.class, new ONT3DPortrayal());
        devicePortrayal.setPortrayalForClass(Splitter.class, new Splitter3DPortrayal());
        devicePortrayal.setPortrayalForClass(GatewayRouter.class, new GatewayRouter3DPortrayal());
        networkPortrayal.setPortrayalForAll(new OLTtoSplitter3DPortrayal());
        failuresPortrayal.setPortrayalForClass(OLTFailure.class, new Failure3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.scaleDisplay(ScenarioDefinitions.FAILURE_DISPLAY_ID, 1.5);
        this.getDisplay(ScenarioDefinitions.FAILURE_DISPLAY_ID).setShowsAxes(true);
        
		
	}

}
