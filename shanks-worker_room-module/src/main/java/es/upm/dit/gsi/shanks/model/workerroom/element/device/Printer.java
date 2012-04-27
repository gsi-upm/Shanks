package es.upm.dit.gsi.shanks.model.workerroom.element.device;

import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementStatusException;

/**
 * Class that represent a printer
 * 
 * @author dlara
 *
 */

public class Printer extends Device{
	
	public static final String STATUS_OK = "OK";
	public static final String STATUS_OFF = "OK";
	public static final String STATUS_DISCONNECTED = "Disconnected";
	public static final String STATUS_LOWINK = "Low Ink";
	public static final String STATUS_NOINK = "No Ink";
	public static final String STATUS_LOWPAPER = "Low Paper";
	public static final String STATUS_NOPAPER = "No Paper";
	
	public static final String PROPERTY_PAPER = "Paper Level"; //Paper level in number of paper sheets
	public static final String PROPERTY_INK = "Ink Level"; //Ink level in percentage
	public static final String PROPERTY_ETHERNET_CONNECTION = "Connection"; //Ethernet connection
	public static final String PROPERTY_POWER = "Power"; //Power supply
	
	
	
	public Printer(String id, String initialState, boolean isGateway)
			throws UnsupportedNetworkElementStatusException {
		super(id, initialState, isGateway);
	}
	
	
	@Override
	public void checkProperties()
			throws UnsupportedNetworkElementStatusException {
		Integer paper = (Integer) this.getProperty(Printer.PROPERTY_PAPER);
		Double ink = (Double) this.getProperty(Printer.PROPERTY_INK);
		Boolean ethernet = (Boolean) this.getProperty(Printer.PROPERTY_ETHERNET_CONNECTION);
		Boolean power = (Boolean) this.getProperty(Printer.PROPERTY_POWER);
		
		if(paper >= 25 && ink >= 0.10 && ethernet && power){
			this.setCurrentStatus(Printer.STATUS_OK, true);
			this.setCurrentStatus(Printer.STATUS_DISCONNECTED, false);
			this.setCurrentStatus(Printer.STATUS_LOWINK, false);
			this.setCurrentStatus(Printer.STATUS_LOWPAPER, false);
			this.setCurrentStatus(Printer.STATUS_NOINK, false);
			this.setCurrentStatus(Printer.STATUS_NOPAPER, false);
			this.setCurrentStatus(Printer.STATUS_OFF, false);
		}
		if(!ethernet){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_DISCONNECTED, true);
		}
		if(!power){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_OFF, true);
		}
		if(paper < 25 && paper > 0){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_LOWPAPER, true);
		}
		if(paper == 0){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_NOPAPER, true);
		}
		if(ink < 0.10 && ink > 0){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_LOWINK, false);
		}
		if(ink == 0){
			this.setCurrentStatus(Printer.STATUS_OK, false);
			this.setCurrentStatus(Printer.STATUS_NOINK, false);
		}
		
	}

	@Override
	public void checkStatus() throws UnsupportedNetworkElementStatusException {	
		HashMap<String, Boolean> status = this.getStatus();
		if(status.get(Printer.STATUS_OK)){
			this.changeProperty(Printer.PROPERTY_ETHERNET_CONNECTION, true);
			this.changeProperty(Printer.PROPERTY_INK, 1.0);
			this.changeProperty(Printer.PROPERTY_PAPER, 500);
			this.changeProperty(Printer.PROPERTY_POWER, true);
		}
		if(status.get(Printer.STATUS_DISCONNECTED)){
			this.changeProperty(Printer.PROPERTY_ETHERNET_CONNECTION, false);
		}
		if(status.get(Printer.STATUS_LOWINK)){
			this.changeProperty(Printer.PROPERTY_INK, 0.10);
		}
		if(status.get(Printer.STATUS_LOWPAPER)){
			this.changeProperty(Printer.PROPERTY_PAPER, 25);
		}
		if(status.get(Printer.STATUS_NOINK)){
			this.changeProperty(Printer.PROPERTY_INK, 0.0);
		}
		if(status.get(Printer.STATUS_NOPAPER)){
			this.changeProperty(Printer.PROPERTY_PAPER, 0);
		}
		if(status.get(Printer.STATUS_OFF)){
			this.changeProperty(Printer.PROPERTY_POWER, false);
		}
	}

	
	
	
	@Override
	public void fillIntialProperties() {
		this.addProperty(Printer.PROPERTY_INK, 1.0);
		this.addProperty(Printer.PROPERTY_ETHERNET_CONNECTION, true);
		this.addProperty(Printer.PROPERTY_PAPER, 500);
		this.addProperty(Printer.PROPERTY_POWER, true);
	}
	
	@Override
	public void setPossibleStates() {
		this.addPossibleStatus(Printer.STATUS_OK);
		this.addPossibleStatus(Printer.STATUS_DISCONNECTED);
		this.addPossibleStatus(Printer.STATUS_LOWINK);
		this.addPossibleStatus(Printer.STATUS_LOWPAPER);
		this.addPossibleStatus(Printer.STATUS_NOINK);
		this.addPossibleStatus(Printer.STATUS_NOPAPER);
		this.addPossibleStatus(Printer.STATUS_OFF);
		
	}
	
	public double getInkLevel(){
		return (Double) this.getProperty(PROPERTY_INK);
	}
	
	public void setInkLevel(double ink) throws UnsupportedNetworkElementStatusException{
		this.changeProperty(PROPERTY_INK, ink);
	}
	
	public void decreaseInk(double percentaje) throws UnsupportedNetworkElementStatusException{
		double newInk = this.getInkLevel() - percentaje;
		this.setInkLevel(newInk);
	}
	
	public double getPaperLevel(){
		return (Integer) this.getProperty(PROPERTY_PAPER);
	}
	
	public void setPaperLevel(double paper) throws UnsupportedNetworkElementStatusException{
		this.changeProperty(PROPERTY_PAPER, paper);
	}
	
	public void decreasePaper(double paper) throws UnsupportedNetworkElementStatusException{
		double newPaper = this.getPaperLevel() - paper;
		this.setPaperLevel(newPaper);
	}
	

	

}
