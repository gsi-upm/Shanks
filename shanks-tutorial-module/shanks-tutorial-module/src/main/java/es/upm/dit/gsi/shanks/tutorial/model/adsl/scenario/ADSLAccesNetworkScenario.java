/**
 * 
 */
package es.upm.dit.gsi.shanks.tutorial.model.adsl.scenario;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

import es.upm.dit.gsi.shanks.model.element.exception.TooManyConnectionException;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;
import es.upm.dit.gsi.shanks.model.failure.Failure;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.AlreadyConnectedScenarioException;
import es.upm.dit.gsi.shanks.model.scenario.exception.DuplicatedIDException;
import es.upm.dit.gsi.shanks.model.scenario.exception.NonGatewayDeviceException;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.exception.UnsupportedScenarioStatusException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.element.device.DSLAM;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.element.link.CopperPair;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.failure.ISPCongestionFailure;
import es.upm.dit.gsi.shanks.tutorial.model.adsl.scenario.portrayal.ADSLAccessNetworkScenario2DPortrayal;
import es.upm.dit.gsi.shanks.tutorial.model.han.scenario.HANScenario;

/**
 * @author a.carrera
 *
 */
public class ADSLAccesNetworkScenario extends ComplexScenario {

    public static final String STORM = "STORM";
    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String SUNNY = "SUNNY";
	
	public ADSLAccesNetworkScenario(String type, String initialState,
			Properties properties)
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, NonGatewayDeviceException,
			AlreadyConnectedScenarioException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		super(type, initialState, properties);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addNetworkElements()
	 */
	@Override
	public void addNetworkElements()
			throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, DuplicatedIDException {
		
		DSLAM dslam = new DSLAM("DSLAM-ISP");
		
		for (int i = 0; i<8; i++){
			CopperPair tempCP = new CopperPair("CP-"+i);
			dslam.connectToLink(tempCP);
			this.addNetworkElement(tempCP);
		}
		
		this.addNetworkElement(dslam);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#addPossibleFailures()
	 */
	@Override
	public void addPossibleFailures() {
        this.addPossibleFailure(ISPCongestionFailure.class, this.getNetworkElement("DSLAM-ISP"));
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario2DPortrayal()
	 */
	@Override
	public Scenario2DPortrayal createScenario2DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
//				return null;
		return new ADSLAccessNetworkScenario2DPortrayal(this, 200, 200);
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#createScenario3DPortrayal()
	 */
	@Override
	public Scenario3DPortrayal createScenario3DPortrayal()
			throws DuplicatedPortrayalIDException, ScenarioNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#getPenaltiesInStatus(java.lang.String)
	 */
	@Override
    public HashMap<Class<? extends Failure>, Double> getPenaltiesInStatus(
            String status) throws UnsupportedScenarioStatusException {

        if (status.equals(ADSLAccesNetworkScenario.STORM)) {
            return this.getStormPenalties();
        } else if (status.equals(ADSLAccesNetworkScenario.EARTHQUAKE)) {
            return this.getEarthquakePenalties();
        } else if (status.equals(ADSLAccesNetworkScenario.SUNNY)) {
            return this.getSunnyPenalties();
        } else {
            throw new UnsupportedScenarioStatusException();
        }

    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getSunnyPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

//        penalties.put(LostSynchronismFailure.class, 1.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getEarthquakePenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

//        penalties.put(LostSynchronismFailure.class, 10.0);

        return penalties;
    }

    /**
     * @return
     */
    private HashMap<Class<? extends Failure>, Double> getStormPenalties() {
        HashMap<Class<? extends Failure>, Double> penalties = new HashMap<Class<? extends Failure>, Double>();

//        penalties.put(LostSynchronismFailure.class, 3.0);

        return penalties;
    }

	/* (non-Javadoc)
	 * @see es.upm.dit.gsi.shanks.model.scenario.Scenario#setPossibleStates()
	 */
	@Override
	public void setPossibleStates() {
        this.addPossibleStatus(ADSLAccesNetworkScenario.STORM);
        this.addPossibleStatus(ADSLAccesNetworkScenario.EARTHQUAKE);
        this.addPossibleStatus(ADSLAccesNetworkScenario.SUNNY);
	}

	@Override
	public void addScenarios() throws UnsupportedNetworkElementStatusException,
			TooManyConnectionException, UnsupportedScenarioStatusException,
			DuplicatedIDException, NonGatewayDeviceException,
			AlreadyConnectedScenarioException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {

        Properties p = this.getProperties();
        for (int i=0; i<8; i++)
        	this.addScenario(HANScenario.class, "HAN"+i, HANScenario.STATUS_SUNNY, p, "Modem", "CP-"+i);
	}

}
