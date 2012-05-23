package es.upm.dit.gsi.shanks.workerroom.model.element.portrayal;

import java.awt.Color;
import java.util.HashMap;

import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device3DPortrayal;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Printer;

public class Printer3DPortrayal extends Device3DPortrayal{

	@Override
	public Color getDeviceColor(Device device) {
		HashMap<String, Boolean> status = device.getStatus();
        if (status.get(Printer.STATUS_OK)) {
            return Color.green;
        }else{
        	return Color.red;
        }
	}

	@Override
	public Color getLabelColor(Device device) {
		HashMap<String, Boolean> status = device.getStatus();
        if (status.equals(Printer.STATUS_OK)) {
            return Color.blue;
        }else{
        	return Color.red;
        }
	}

	private static final long serialVersionUID = 7192217228143784392L;
}
