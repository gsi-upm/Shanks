package es.upm.dit.gsi.shanks.datacenter.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.datacenter.model.element.device.Router;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

public class Router2DPortrayal extends Device2DPortrayal {

	// TODO make the animation for stealing connection.
	private int number;

	public Router2DPortrayal() {
		super();
		this.number = 0;
	}

	public Router2DPortrayal(int number) {
		super();
		this.number = number;
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
		if (status.get(Router.STATUS_OK)) {
			graphics.setColor(Color.green);
		} else if (status.get(Router.STATUS_OFF)) {
			graphics.setColor(Color.gray);
		} else if (status.get(Router.STATUS_CONGESTED)) {
			graphics.setColor(Color.yellow);
		} else if (status.get(Router.STATUS_NOISP_SERVICE)
				|| status.get(Router.STATUS_DISCONNECTED)) {
			graphics.setColor(Color.red);
		}

		// Draw the devices
		final int x = (int) (info.draw.x - width / 2.0)*(1+this.number);
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
