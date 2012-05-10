package es.upm.dit.gsi.shanks.shanks_isp_module.model.element.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.element.device.ISPGateway;

public class ISPGateway2DPortrayal extends Device2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3612739437019789118L;

	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        HashMap<String, Boolean> status = device.getStatus();
        
        if (status.get(ISPGateway.STATUS_OK)) {
            graphics.setColor(Color.green);
        } else{
            graphics.setColor(Color.red); 
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }
}
