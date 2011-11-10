package es.upm.dit.gsi.shanks;

import sim.portrayal3d.continuous.*;
import sim.portrayal3d.simple.*;
import sim.engine.*;
import sim.display.*;
import sim.display3d.*;
import javax.swing.*;
import java.awt.*;
import sim.util.*;


public class Model3DGUI extends GUIState {
	
	public Display3D display;
    public JFrame displayFrame;
    
    ContinuousPortrayal3D elementsPortrayal = new ContinuousPortrayal3D();

    public static void main(String[] args){
    	new Model3DGUI().createController();
    }

    public Model3DGUI(){ 
    	super(new Model( System.currentTimeMillis())); 
    }
    
    public Model3DGUI(SimState state){ 
    	super(state); 
    }
    
    public static String getName(){ 
    	return "Tutorial 6: Planets"; 
    }
    
    public static Object getInfo(){ 
    	return "<H2>Tutorial 6</H2> Planetary Orbits"; 
    }

    public void start(){
    	super.start();
    	setupPortrayals();
    }

    public void load(SimState state){
    	super.load(state);
    	setupPortrayals();
    }

    public void quit(){
    	super.quit();
    	if (displayFrame!=null){ 
    		displayFrame.dispose();
       		displayFrame = null;
    		display = null;
    	}
    }
    
    public void setupPortrayals(){
    	display.destroySceneGraph();
    	Model mod = (Model) state;
        elementsPortrayal.setField(mod.elements3d);
        
        // planetary colors
        Color colors[] = {Color.green};
        
        Bag objs = mod.elements.getAllObjects();
        for(int i=0;i<objs.size();i++){
            elementsPortrayal.setPortrayalForObject(
                objs.objs[i], new SpherePortrayal3D(colors[0], 
                        (float) (50),
                    50));
            display.reset();
            display.createSceneGraph();
        }
    }
    
    public void init(Controller c){
    	super.init(c);

    	Model mod = (Model) state;
    	elementsPortrayal.setField(mod.elements3d);

    	display = new Display3D(600,600,this,1);
    	display.attach(elementsPortrayal, "The Solar System");
	
    // scale down to fit the region a little beyond pluto into the 2x2x2 box
    	display.scale(1.0/250);
    	displayFrame = display.createFrame();
        c.registerFrame(displayFrame);
        displayFrame.setVisible(true);
        }
    

	}

