package es.upm.dit.gsi.shanks.magneto.model.steppables;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.Server;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ServerGateway;
import es.upm.dit.gsi.shanks.magneto.model.element.device.UserGateway;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.ScenarioPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;
import sim.engine.SimState;
import sim.engine.Steppable;

public class ErrorsInDevicesPainter implements Steppable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581488603116343228L;
	
	public static final String ERRORS_IN_DEVICE_ID = "Errors in devices";
	
	public int posicion;
	public int longitud;
	public double[] errors;
	public boolean flag;
	
	public ErrorsInDevicesPainter(){
		this.posicion = 0;
		this.longitud = 100;
		this.flag = false;
		errors = new double[longitud];		
	}

	public void step(SimState sim) {
		ShanksSimulation simulation = (ShanksSimulation) sim;
		try{
			ScenarioPortrayal scenarioPortrayal = simulation
                    .getScenarioPortrayal();
			if(flag){
				scenarioPortrayal.removeDataSerieFromHistogram(ERRORS_IN_DEVICE_ID, 0);
			}
			ISPGateway isp = (ISPGateway) simulation.getScenario().getNetworkElement("ISP Gateway");
			Server server = (Server) simulation.getScenario().getNetworkElement("Server");
			ServerGateway serverGateway = (ServerGateway) simulation.getScenario().getNetworkElement("Server Gateway");
			UserGateway userGateway = (UserGateway) simulation.getScenario().getNetworkElement("User Gateway");
			
			int failuresInISP = isp.getCounter();
			int failuresInUserGway = userGateway.getCounter();
			int failuresInServer = server.getCounter();
			int failuresInServerGway = serverGateway.getCounter();
			
			
			
			
			if(failuresInISP > 1){
				System.out.println("------------->" + failuresInISP);
				errors[posicion] = 1;
				isp.resetCounter();
				posicion++;
				
			}
			if(failuresInServer > 1){
				errors[posicion] = 2;
				server.resetCounter();
				posicion++;
				
			}
			if(failuresInServerGway > 1){
				errors[posicion] = 3;
				serverGateway.resetCounter();
				posicion++;
				
			}
			if(failuresInUserGway > 1){
				errors[posicion] = 4;
				userGateway.resetCounter();
				posicion++;
				
			}
			
			scenarioPortrayal.addDataSerieToHistogram(ERRORS_IN_DEVICE_ID, errors, 5);
			flag = true;
			
			
			
		} catch (DuplicatedPortrayalIDException e) {
            e.printStackTrace();
        } catch (ScenarioNotFoundException e) {
            e.printStackTrace();
        } catch (ShanksException e) {
            e.printStackTrace();
        }
	}

}
