package es.upm.dit.gsi.shanks.model.workerroom.scenario.portrayal;

import sim.field.grid.SparseGrid2D;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.failure.portrayal.Failure2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Computer;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Printer;
import es.upm.dit.gsi.shanks.model.workerroom.element.device.Router;
import es.upm.dit.gsi.shanks.model.workerroom.element.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.element.portrayal.EthernetLink2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.element.portrayal.Printer2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.element.portrayal.Router2DPortrayal;
import es.upm.dit.gsi.shanks.model.workerroom.failure.ComputerPruebaFailure;
import es.upm.dit.gsi.shanks.model.workerroom.failure.RouterCongestion;
import es.upm.dit.gsi.shanks.model.workerroom.failure.WireBroken;
import es.upm.dit.gsi.shanks.model.workerroom.failure.portrayal.FailuresPortrayal;

public class WorkerRoomScenario2DPortrayal extends Scenario2DPortrayal{

	public static final String FAILURE_DISPLAY_ID = "Current Failures";
    public static final String FAILURE_PORTRAYAL_ID = "Failures";
	
	public WorkerRoomScenario2DPortrayal(Scenario scenario, int width,
			int height) throws DuplicatedPortrayalIDException {
		super(scenario, width, height);
	}

	@Override
	public void addPortrayals() {
		SparseGrid2D failuresGrid = new SparseGrid2D(100, 100);
        SparseGridPortrayal2D failuresPortrayal = new SparseGridPortrayal2D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(FAILURE_DISPLAY_ID, FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
		
	}

	@Override
	public void placeElements() {
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC1"), 10, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC2"), 10, 30);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC3"), 10, 40);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC4"), 10, 50);
		this.situateDevice((Device)this.getScenario().getNetworkElement("PC5"), 10, 60);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Printer"), 50, 20);
		this.situateDevice((Device)this.getScenario().getNetworkElement("Router"), 50, 50);
		
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink1"));
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink2"));
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink3"));
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink4"));
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink5"));
		this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink6"));

		
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal2D devicePortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		NetworkPortrayal2D networkPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        SparseGridPortrayal2D failuresPortrayal = (SparseGridPortrayal2D) this.getPortrayals().get(FAILURE_DISPLAY_ID).get(FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Printer.class, new Printer2DPortrayal());
        devicePortrayal.setPortrayalForClass(Router.class, new Router2DPortrayal());
        networkPortrayal.setPortrayalForAll(new EthernetLink2DPortrayal());
        failuresPortrayal.setPortrayalForAll(new FailuresPortrayal());
	}

}
