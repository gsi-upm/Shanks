package es.upm.dit.gsi.shanks.model.element.device.portrayal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;
import es.upm.dit.gsi.shanks.model.element.device.Device;

public abstract class Device2DPortrayal extends SimplePortrayal2D {

    /**
     * 
     */
    private static final long serialVersionUID = -5132594800897540031L;

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        Device device = (Device) object;
        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);        
        graphics.setColor(Color.gray);
        graphics.fillOval(x, y, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString(device.getID(), x - 3, y);

    }
    
    public void putImage(String path, int x, int y, int w, int h, Graphics2D graphics){
        ImageIcon i = new ImageIcon(path);
        Image image = i.getImage();
        ImageObserver io = new ImageObserver() {
            
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y,
                    int width, int height) {
                return false;
            }
        };
        
        graphics.drawImage(image, x, y, w*3, h*3, io);
    }
}
