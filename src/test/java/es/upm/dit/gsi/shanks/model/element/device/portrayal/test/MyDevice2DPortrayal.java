package es.upm.dit.gsi.shanks.model.element.device.portrayal.test;

import java.awt.Color;
import java.awt.Graphics2D;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.model.element.device.test.MyDevice;

/**
 * @author a.carrera
 *
 */
public class MyDevice2DPortrayal extends Device2DPortrayal implements Portrayal {

    /**
     * 
     */
    private static final long serialVersionUID = 3180819560173840065L;

    /* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        String status = device.getCurrentStatus();
        if (status.equals(MyDevice.OK_STATUS)) {
            graphics.setColor(Color.green);
        } else if (status.equals(MyDevice.NOK_STATUS)) {
            graphics.setColor(Color.red);
        } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
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
