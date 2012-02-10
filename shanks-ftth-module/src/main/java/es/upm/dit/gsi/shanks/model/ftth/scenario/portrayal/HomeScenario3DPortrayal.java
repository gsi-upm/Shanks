package es.upm.dit.gsi.shanks.model.ftth.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.test.MyDevice3DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.element.link.portrayal.test.MyLink3DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure3DPortrayal;
import es.upm.dit.gsi.shanks.model.failure.test.MyFailure;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;
import es.upm.dit.gsi.shanks.model.ftth.element.device.Splitter;
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
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;

public class HomeScenario3DPortrayal extends Scenario3DPortrayal{

	public HomeScenario3DPortrayal(Scenario scenario, long width, long height,
			long length) throws DuplicatedPortrayalIDException {
		super(scenario, width, height, length);
		// TODO Auto-generated constructor stub
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
		this.situateDevice((Device)this.getScenario().getNetworkElement("OLT"), 0, 0, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Splitter"), 200, 200, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT1"), 400, 400, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT2"), 600, 400, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT3"), 800, 400, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT4"), 400, 600, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT5"), 600, 600, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT6"), 800, 600, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT7"), 400, 800, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT8"), 600, 800, 0);
		this.situateDevice((Device)this.getScenario().getNetworkElement("ONT9"), 800, 800, 0);
	
		this.drawLink((Link)this.getScenario().getNetworkElement("OLTtoSplitterLink"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("Link3"));
		
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(ScenarioDefinitions.FAILURE_DISPLAY_ID).get(ScenarioDefinitions.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(OLT.class, new OLT3DPortrayal());
        devicePortrayal.setPortrayalForClass(ONT.class, new ONT3DPortrayal());
        devicePortrayal.setPortrayalForClass(Splitter.class, new Splitter3DPortrayal());
        networkPortrayal.setPortrayalForAll(new OLTtoSplitter3DPortrayal());
        failuresPortrayal.setPortrayalForClass(OLTFailure.class, new Failure3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.scaleDisplay(ScenarioDefinitions.FAILURE_DISPLAY_ID, 1.5);
      this.getDisplay(ScenarioDefinitions.FAILURE_DISPLAY_ID).setShowsAxes(true);
        
		
	}

}
