package es.upm.dit.gsi.shanks.model.ftth.element.device.portrayal.OLT;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.ftth.element.device.DeviceDefinitions;
import es.upm.dit.gsi.shanks.model.ftth.element.device.OLT;

public class OLT2DPortrayal extends Device2DPortrayal implements Portrayal{

	private static final long serialVersionUID = -3806767132917010661L;

	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        String status = device.getCurrentStatus();
        if (status.equals(OLT.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(OLT.NOK_STATUS)) {
            graphics.setColor(Color.red);
        } else if (status.equals(OLT.LOW_REC_LASER_POWER)) {
            graphics.setColor(Color.black);
        } else if (status.equals(OLT.LOW_EMI_LASER_POWER)) {
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
