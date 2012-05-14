package es.upm.dit.gsi.shanks.networkattacks.util.networkelements.portrayals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

public class Router2DPortrayal extends Device2DPortrayal {

	// TODO make the animation for stealing connection.

	public Router2DPortrayal() {
		super();
	}

	public Router2DPortrayal(int number) {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal
	 * #draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
	 */
	@Override
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

		Device device = (Device) object;
		final double width = 10;
		final double height = 10;

		HashMap<String, Boolean> status = device.getStatus();
		if (status.get(RouterDNS.STATUS_OK)) {
			graphics.setColor(Color.green);
		} else if (status.get(RouterDNS.STATUS_OFF)) {
			graphics.setColor(Color.gray);
		} else if (status.get(RouterDNS.STATUS_CONGESTED)) {
			graphics.setColor(Color.yellow);
		} else if (status.get(RouterDNS.STATUS_NOISP_SERVICE)
				|| status.get(RouterDNS.STATUS_DISCONNECTED)) {
			graphics.setColor(Color.red);
		}

		// Draw the devices
		final int x = (int) (info.draw.x - width / 2.0);
		final int y = (int) (info.draw.y - height / 2.0);
		final int w = (int) (width);
		final int h = (int) (height);
		graphics.fillOval(x, y, w + 5, h + 5);

		// Draw the devices ID
		graphics.setColor(Color.black);
		graphics.drawString(device.getID(), x - 3, y + 1);

	}
	private static final long serialVersionUID = -9152169914992904544L;
}
