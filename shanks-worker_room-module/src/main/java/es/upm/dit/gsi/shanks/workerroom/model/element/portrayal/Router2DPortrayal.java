package es.upm.dit.gsi.shanks.workerroom.model.element.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;
import es.upm.dit.gsi.shanks.networkattacks.util.Values;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.Computer;
import es.upm.dit.gsi.shanks.workerroom.model.element.device.LANRouter;

public class Router2DPortrayal extends Device2DPortrayal implements Portrayal{
	
	
	/* (non-Javadoc)
     * @see es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal#draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        HashMap<String, Boolean> status = device.getStatus();
        List<String> okStatus = new ArrayList<String>();
        List<String> nokStatus = new ArrayList<String>();
        
        for(String s : status.keySet()){
            if(s.equals(Computer.STATUS_OK)){
            	if(status.get(s))
            		okStatus.add(s);
            }
        }
        if (status.get(LANRouter.STATUS_OK)) {
            graphics.setColor(Color.green);
        }else if(status.get(LANRouter.STATUS_CONGESTED)){
        	graphics.setColor(Color.blue);
        }else{
            graphics.setColor(Color.black);
        }

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);
        graphics.fillRect(x, y, w, h);

        
        // Draw the devices ID ID
        if(Values.VISIBLE_ID){
	        graphics.setColor(Color.black);
	        graphics.drawString(device.getID(), x - 3, y);
        }

    }

}
