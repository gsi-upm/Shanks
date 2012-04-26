package es.upm.dit.gsi.shanks.hackerhan.model.adsl.scenario.portrayal;

import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.util.Double2D;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Computer;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.EthernetRouter;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.ModemADSL;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.Smartphone;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.WifiAccessPoint;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal.Computer2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal.EthernetRouter2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal.ModemADSL2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal.Smartphone2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.device.portrayal.WifiAccessPoint2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.EthernetCable;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.InternalBus;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.protrayal.EthernetCable2DPortrayal;
import es.upm.dit.gsi.shanks.hackerhan.model.han.element.link.protrayal.InternalBus2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ShanksMath;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;


/**
 * @author a.carrera
 *
 */
public class ADSLAccessNetworkScenario2DPortrayal extends ComplexScenario2DPortrayal {

	public ADSLAccessNetworkScenario2DPortrayal(Scenario scenario, int width, int height)
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		super(scenario, width, height);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#addPortrayals()
	 */
	@Override
	public void addPortrayals() {
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal#placeElements()
	 */
	@Override
	public void placeElements() {
        ComplexScenario cs = (ComplexScenario) this.getScenario();       
        this.situateDevice((Device)cs.getNetworkElement("DSLAM-ISP"), 75, 70);
        for (int i=0; i<8; i++)
        	this.drawLink((Link)cs.getNetworkElement("CP-"+i));
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal#setupPortrayals()
	 */
	@Override
	public void setupPortrayals() {
        ContinuousPortrayal2D devicesPortrayal = (ContinuousPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.DEVICES_PORTRAYAL);
        NetworkPortrayal2D linksPortrayal = (NetworkPortrayal2D) this.getPortrayals().get(Scenario2DPortrayal.MAIN_DISPLAY_ID).get(ScenarioPortrayal.LINKS_PORTRAYAL);
        devicesPortrayal.setPortrayalForClass(Computer.class, new Computer2DPortrayal());
        devicesPortrayal.setPortrayalForClass(EthernetRouter.class, new EthernetRouter2DPortrayal());
        devicesPortrayal.setPortrayalForClass(ModemADSL.class, new ModemADSL2DPortrayal());
        devicesPortrayal.setPortrayalForClass(WifiAccessPoint.class, new WifiAccessPoint2DPortrayal());
        devicesPortrayal.setPortrayalForClass(Smartphone.class, new Smartphone2DPortrayal());
                
        linksPortrayal.setPortrayalForClass(EthernetCable.class, new EthernetCable2DPortrayal());
        linksPortrayal.setPortrayalForClass(InternalBus.class, new InternalBus2DPortrayal());
//        linksPortrayal.setPortrayalForAll(new Link2DPortrayalChooser());
	}

	@Override
	public void placeScenarios() throws DuplicatedPortrayalIDException,
			ScenarioNotFoundException {
        ComplexScenario cs = (ComplexScenario) this.getScenario();
        for (int i=0; i<8; i++){
        	//TODO learn for what is made the four double of situateScenario(). there no was before. 
        	if (i<3){
        		this.situateScenario(cs.getScenario("HAN"+i), new Double2D(0,i*100), ShanksMath.ANGLE_0, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        	} else if(i==3){
        		this.situateScenario(cs.getScenario("HAN"+i), new Double2D(100,0), ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        	} else if(i==4){
        		this.situateScenario(cs.getScenario("HAN"+i), new Double2D(100,200), ShanksMath.ANGLE_90, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        	} else {
        		this.situateScenario(cs.getScenario("HAN"+i), new Double2D(200,(i-5)*100), ShanksMath.ANGLE_180, ShanksMath.ANGLE_0, ShanksMath.ANGLE_0);
        	}
        }
	}

}
