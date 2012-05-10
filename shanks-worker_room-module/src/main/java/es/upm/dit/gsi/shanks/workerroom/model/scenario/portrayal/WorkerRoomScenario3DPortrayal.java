package es.upm.dit.gsi.shanks.workerroom.model.scenario.portrayal;

import sim.field.continuous.Continuous3D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Computer;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Router;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Computer3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.EthernetLink3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Printer3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.portrayal.Router3DPortrayal;

public class WorkerRoomScenario3DPortrayal extends Scenario3DPortrayal{

	public WorkerRoomScenario3DPortrayal(Scenario scenario, long width,
			long height, long length) throws DuplicatedPortrayalIDException {
		super(scenario, width, height, length);

	}

	@Override
	public void addPortrayals() {
		Continuous3D failuresGrid = new Continuous3D(5, 100, 100, 100);
        ContinuousPortrayal3D failuresPortrayal = new ContinuousPortrayal3D();
        failuresPortrayal.setField(failuresGrid);
        try {
            this.addPortrayal(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID, WorkerRoomScenario2DPortrayal.FAILURE_PORTRAYAL_ID, failuresPortrayal);
        } catch (DuplicatedPortrayalIDException e) {            
            e.printStackTrace();
        }
		
	}

	@Override
	public void placeElements() {
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC1"), 100.0, 20.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC2"), 100.0, 120.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC3"), 100.0, 220.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC4"), 100.0, 320.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC5"), 100.0, 420.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC6"), 100.0, 520.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC7"), 100.0, 620.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("PC8"), 100.0, 720.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("Router"), 600.0, 370.0, 100.0);
        this.situateDevice((Device)this.getScenario().getNetworkElement("Printer"), 350.0, 370.0, 100.0);
        this.drawLink((Link)this.getScenario().getNetworkElement("EthernetLink"));
		
	}

	@Override
	public void setupPortrayals() {
		ContinuousPortrayal3D devicePortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		ContinuousPortrayal3D printerPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
		ContinuousPortrayal3D routerPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);	
		NetworkPortrayal3D networkPortrayal = (NetworkPortrayal3D) this.getPortrayals().get(Scenario3DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        ContinuousPortrayal3D failuresPortrayal = (ContinuousPortrayal3D) this.getPortrayals().get(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID).get(WorkerRoomScenario2DPortrayal.FAILURE_PORTRAYAL_ID);
        devicePortrayal.setPortrayalForClass(Computer.class, new Computer3DPortrayal());
        printerPortrayal.setPortrayalForClass(Printer.class, new Printer3DPortrayal());
        routerPortrayal.setPortrayalForClass(Router.class, new Router3DPortrayal());
        networkPortrayal.setPortrayalForAll(new EthernetLink3DPortrayal());
        //failuresPortrayal.setPortrayalForClass(MyFailure.class, new Failure3DPortrayal());
        
        this.scaleDisplay(Scenario3DPortrayal.MAIN_DISPLAY_ID, 1.5);
        this.scaleDisplay(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID, 1.5);
        this.getDisplay(WorkerRoomScenario2DPortrayal.FAILURE_DISPLAY_ID).setShowsAxes(true);
        
		
	}

}
