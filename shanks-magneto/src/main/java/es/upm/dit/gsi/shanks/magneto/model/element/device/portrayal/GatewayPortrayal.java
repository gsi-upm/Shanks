package es.upm.dit.gsi.shanks.magneto.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import sim.portrayal.DrawInfo2D;
import es.upm.dit.gsi.shanks.magneto.model.element.device.ISPGateway;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

public class GatewayPortrayal extends Device2DPortrayal{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2875023906624774863L;
	
	private static final String ISP_GATEWAY_IMAGE_PATH = "/home/dlara/workspace/Shanks-Magneto/shanks-magneto/imagenes/ISP.jpg";
	private static final String ERROR_ISP_GATEWAY_IMAGE_PATH = "/home/dlara/workspace/Shanks-Magneto/shanks-magneto/imagenes/ISPError.jpg";
	
	
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
        	this.putImage(ISP_GATEWAY_IMAGE_PATH, x, y - 50, 3*w, 3*h, graphics);
        }else{
        	this.putImage(ERROR_ISP_GATEWAY_IMAGE_PATH, x , y - 50, 3*w, 3*h, graphics);
        }

    }

}
