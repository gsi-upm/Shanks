package es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.ONT;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.ONT;

public class ONT2DPortrayal extends Device2DPortrayal implements Portrayal{

	private static final long serialVersionUID = -7201115272613038415L;
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        String status = device.getCurrentStatus();
        if (status.equals(ONT.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(ONT.NOK_STATUS)) {
            graphics.setColor(Color.red);
        } else if (status.equals(ONT.LOW_EMI_LASER_POWER)) {
            graphics.setColor(Color.black);
        } else if (status.equals(ONT.LOW_REC_LASER_POWER)) {
            graphics.setColor(Color.blue);
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
