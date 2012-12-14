package es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal;

import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

public class UserGatewayPortrayal extends Device2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2362259939385311112L;

	private static final String GATEWAY_IMAGE_PATH = "/home/dlara/workspace/Shanks-Magneto/shanks-magneto/imagenes/gateway.png";
	private static final String ERROR_GATEWAY_IMAGE_PATH = "/home/dlara/workspace/Shanks-Magneto/shanks-magneto/imagenes/gatewayError.png";
	
	
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;
       
        HashMap<String, Boolean> status = device.getStatus();
        
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
       
        if(status.get(ISPGateway.STATUS_OK)){
        	this.putImage(GATEWAY_IMAGE_PATH, x - 15, y - 25, w, h, graphics);
        }else{
        	this.putImage(ERROR_GATEWAY_IMAGE_PATH, x - 15, y - 25, w, h, graphics);
        }
	}
}
