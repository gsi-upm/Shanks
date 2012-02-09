package es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

public class HomeScenario2DPortrayal extends Scenario2DPortrayal{

	
	/**
     * @param scenario
     * @param width
     * @param height
     * @throws DuplicatedPortrayalIDException
     */
	public HomeScenario2DPortrayal(Scenario scenario, int width, int height)
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

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
     */
	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("OLT"), 0, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter"), 20, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT1"), 40, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT2"), 60, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT3"), 80, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT4"), 40, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT5"), 60, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT6"), 80, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT7"), 40, 40);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT8"), 60, 40);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT9"), 80, 40);
	
		this.drawLink((Link)this.getScenario().getNetworkElement("OLTtoSplitterLink"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link3"));
	}

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
     */
	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID).get(MyHyperComplexScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(OLT.class, new OLT2DPortrayal());
        devicePortrayal.setPortrayalForClass(ONT.class, new ONT2DPortrayal());
        devicePortrayal.setPortrayalForClass(Splitter.class, new Splitter2DPortrayal());

        networkPortrayal.setPortrayalForAll(new OLTtoSplitter2DPortrayal());
        failuresPortrayal.setPortrayalForClass(OLTFailure.class, new Failure2DPortrayal());
		
	}

}
