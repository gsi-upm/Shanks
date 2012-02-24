package es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.GatewayRouter;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.GatewayRouter.GatewayRouter2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.OLT.OLT2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.ONT.ONT2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.Splitter.Splitter2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.link.portrayal.OLTtoSplitter2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.failure.OLTFailure;
import es.upm.dit.gsi.shanks.model.ftth.scenario.ScenarioDefinitions;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public class OfficeScenario2DPortrayal extends Scenario2DPortrayal{

	
	/**
     * @param scenario
     * @param width
     * @param height
     * @throws DuplicatedPortrayalIDException
     */
	public OfficeScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	@Override
	public void addPortrayals() {
		SparseGrid2D failuresGrid = new SparseGrid2D(100, 100);
        SparseGridPortrayal2D failuresPortrayal = new SparseGridPortrayal2D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(ScenarioDefinitions.FAILURE_DISPLAY_ID, ScenarioDefinitions.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
		
	}

	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("OLT"), 20, 100);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter1"), 50, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter2"), 50, 100);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter3"), 50, 150);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT1"), 100, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT2"), 100, 100);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT3"), 100, 150);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router1"), 150, 25);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router2"), 150, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router3"), 150, 75);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router4"), 150, 100);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router5"), 150, 125);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router6"), 150, 150);
	
		this.drawLink((Link)this.getScenario().getNetworkElement("Link0"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link3"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link4"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link5"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link6"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link7"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link8"));
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(ScenarioDefinitions.FAILURE_DISPLAY_ID).get(ScenarioDefinitions.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(OLT.class, new OLT2DPortrayal());
        devicePortrayal.setPortrayalForClass(ONT.class, new ONT2DPortrayal());
        devicePortrayal.setPortrayalForClass(Splitter.class, new Splitter2DPortrayal());
        devicePortrayal.setPortrayalForClass(GatewayRouter.class, new GatewayRouter2DPortrayal());
        networkPortrayal.setPortrayalForAll(new OLTtoSplitter2DPortrayal());
        failuresPortrayal.setPortrayalForClass(OLTFailure.class, new Failure2DPortrayal());
		
		
	}

}
