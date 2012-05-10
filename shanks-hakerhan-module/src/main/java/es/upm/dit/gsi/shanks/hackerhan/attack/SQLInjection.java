package es.upm.dit.gsi.shanks.hackerhan.attack;

import java.util.ArrayList;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.hackerhan.agent.Hacker;

/**
 * Class to represent a SQL Injection Attack.
 * 
 */
public class SQLInjection implements Attack {

	private Hacker hacker;
	
	private ShanksSimulation sim;
	
	private ArrayList<Integer> ports;
	
	private boolean running;
	private boolean success;
	
	public SQLInjection(Hacker hacker, ShanksSimulation sim){
		super();
		this.hacker = hacker;
		this.sim = sim;
		this.ports = new ArrayList<Integer>();
		this.ports.add(80); // HTTP
		this.ports.add(443); // Default SQL
		this.ports.add(8080); // Tomcat
		this.ports.add(3306); // Default SQL
		this.running = false;
		this.success = false;
	}
	
	@Override
	public void execute() {
		this.running = true;
//		Gateway gateway = (Gateway)sim.getScenario().getNetworkElement("Gateway");
//		for (int port : ports){
//			if(gateway.isPortOpen(this.hacker.getID(), port)){
//				// TODO: Try to make the injection.
//			}
//		}
	}

	@Override
	public boolean isSuccessful() {
		return this.success;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

}
