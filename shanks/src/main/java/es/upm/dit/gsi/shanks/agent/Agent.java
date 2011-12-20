package es.upm.dit.gsi.shanks.agent;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import es.upm.dit.gsi.shanks.Simulation;
import es.upm.dit.gsi.shanks.model.ScenarioManager;
import es.upm.dit.gsi.shanks.model.common.Definitions;
import es.upm.dit.gsi.shanks.model.scenario.Device;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.SimplePortrayal2D;

public class Agent extends SimplePortrayal2D implements Steppable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4744430503147830611L;

    public Logger log = Logger.getLogger("Agent");

    public List<Device> faildevices = new ArrayList<Device>();
    public List<Device> totaldevice = new ArrayList<Device>();
    public static boolean repairFlag = true;
    public static String problemDetected = "No problem";

    public Agent() {

    }

    public List<Device> getFailureDevice() {
        Agent a = new Agent();
        log.info("-> getFailureDevice()");
        totaldevice = ScenarioManager.getDeviceFromScenario();

        log.fine("Scenario size: " + totaldevice.size());

        // Make a list with the broken devices
        for (int i = 0; i < totaldevice.size(); i++) {
            if (totaldevice.get(i).getStatus() == 1) {
                // log.info("Error in device: " + totaldevice.get(i).getID());
                faildevices.add(totaldevice.get(i));

            }
        }

        if (faildevices.isEmpty()) {
            log.info("No problems");
        } else {
            for (int j = 0; j < faildevices.size(); j++) {
                log.info("Error in device: " + faildevices.get(j).getID());
            }

        }
        Simulation.problems.setObjectLocation(a, 25, 50);
        return faildevices;
    }

    public void repairDevices() {
        log.info("-> repairDevices()");
        for (Device d : faildevices) {
            log.fine("Device broken: " + d.getID());
            d.setStatus(Definitions.HEALTHY_STATUS);
            log.fine("STATUS " + d.getStatus() + " of Device " + d.getID());
        }
        faildevices.clear();
        log.info("Devices have been repaired");
    }

    public void step(SimState state) {
        getFailureDevice();
        if (!faildevices.isEmpty()) {
            repairDevices();
            if (!faildevices.isEmpty()) {
                repairFlag = false;
                System.out
                        .println("ALERT!! THE AGENT CANNOT REPAIR THE DEVICES");
            }
        }
    }

    public static Image loadImage(String img) {
        return new ImageIcon(Simulation.class.getResource(img)).getImage();
    }

    @Override
    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {

        final double width = 20;
        final double height = 20;

        // Draw the devices
        final int x = (int) (info.draw.x - width / 2.0);
        final int y = (int) (info.draw.y - height / 2.0);
        final int w = (int) (width);
        final int h = (int) (height);

        if (problemDetected.equals(ScenarioManager.dev.getName())) {
            graphics.setColor(Color.green);
        } else {
            graphics.setColor(Color.red);
        }
        graphics.fillOval(325, 15, w, h);

        // Draw the devices ID ID
        graphics.setColor(Color.black);
        graphics.drawString("Problem detected ------> " + problemDetected,
                x - 3, y);
    }

}
