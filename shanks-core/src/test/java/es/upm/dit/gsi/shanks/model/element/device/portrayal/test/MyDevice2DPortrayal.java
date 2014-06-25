package es.upm.dit.gsi.shanks.model.element.device.portrayal.test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;
import java.io.File;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import sim.portrayal.DrawInfo2D;
import sim.portrayal.Portrayal;
import es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal;

/**
 * @author a.carrera
 * 
 */
public class MyDevice2DPortrayal extends Device2DPortrayal implements Portrayal {

    /**
     * 
     */
    private static final long serialVersionUID = 3180819560173840065L;
    private BufferedImage bi = null;
    private ImageObserver io = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.model.element.device.portrayal.Device2DPortrayal
     * #draw(java.lang.Object, java.awt.Graphics2D, sim.portrayal.DrawInfo2D)
     */
    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {


        
        //****************************************
        //*******CODE TO SHOW CIRCLES AS DEVICES**
        //****************************************
        
        // Device device = (Device) object;
        // final double width = 20;
        // final double height = 20;
        //
        // HashMap<String, Boolean> status = device.getStatus();
        // List<String> okStatus = new ArrayList<String>();
        // List<String> nokStatus = new ArrayList<String>();
        //
        // for (String s : status.keySet()) {
        // if (status.get(s)) {
        // nokStatus.add(s);
        // } else {
        // okStatus.add(s);
        // }
        // }
        // if (nokStatus.size() == 0) {
        // graphics.setColor(Color.green);
        // } else if (status.equals(MyDevice.NOK_STATUS)) {
        // graphics.setColor(Color.red);
        // } else if (status.equals(MyDevice.UNKOWN_STATUS)) {
        // graphics.setColor(Color.blue);
        // }
        //
        // // Draw the devices
        // final int x = (int) (info.draw.x - width / 2.0);
        // final int y = (int) (info.draw.y - height / 2.0);
        // final int w = (int) (width);
        // final int h = (int) (height);
        // graphics.fillOval(x, y, w, h);
        //
        // // If you want put and image use this method
        // // this.putImage(path, x, y, w, h, graphics);
        //
        // // Draw the devices ID ID
        // graphics.setColor(Color.black);
        // graphics.drawString(device.getID(), x - 3, y);

        

        //***********************************************
        //*******CODE TO SHOW JPEG WITH CMYK COLORSPACE**
        //***********************************************
        try {
            if (bi == null) {
                String path = "src/main/resources/fileserver.jpg";

                File file = new File(path);

                // Find a suitable ImageReader
                Iterator<ImageReader> readers = ImageIO
                        .getImageReadersByFormatName("JPEG");
                ImageReader reader = null;
                while (readers.hasNext()) {
                    reader = (ImageReader) readers.next();
                    if (reader.canReadRaster()) {
                        break;
                    }
                }

                // Stream the image file (the original CMYK image)
                ImageInputStream input = ImageIO.createImageInputStream(file);
                reader.setInput(input);
                // Read the image raster
                Raster raster = reader.readRaster(0, null);

                // Create a new RGB image
                bi = new BufferedImage(raster.getWidth(), raster.getHeight(),
                        BufferedImage.TYPE_4BYTE_ABGR);

                // Fill the new image with the old raster
                bi.getRaster().setRect(raster);
                if (io == null) {

                    io = new ImageObserver() {

                        @Override
                        public boolean imageUpdate(Image img, int infoflags,
                                int x, int y, int width, int height) {
                            return false;
                        }
                    };
                }
            }

            // Draw the devices
            double width = bi.getWidth();
            double height = bi.getHeight();
            final int x = (int) (info.draw.x - width / 2.0);
            final int y = (int) (info.draw.y - height / 2.0);
            final int w = (int) (width);
            final int h = (int) (height);

            graphics.drawImage(bi, x, y, w, h, io);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
