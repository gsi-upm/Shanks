package es.upm.dit.gsi.shanks;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.display3d.Display3D;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.SimpleInspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal3d.continuous.ContinuousPortrayal3D;
import sim.portrayal3d.network.CylinderEdgePortrayal3D;
import sim.portrayal3d.network.NetworkPortrayal3D;
import sim.portrayal3d.network.SimpleEdgePortrayal3D;
import sim.portrayal3d.network.SpatialNetwork3D;
import sim.portrayal3d.simple.CircledPortrayal3D;



public class Model3DGUI extends GUIState {
	
	public Logger log = Logger.getLogger("ModelGUI");
	
	public Display3D display;
    public JFrame displayFrame; 
    public Display2D displayMessage;
    public JFrame frameMessage;
    SparseGridPortrayal2D problems = new SparseGridPortrayal2D();
    static ContinuousPortrayal3D elementsPortrayal = new ContinuousPortrayal3D();
    NetworkPortrayal3D edgePortrayal = new NetworkPortrayal3D();
    public static List<Device> devices = new ArrayList<Device>();

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
    	return "SHANKS"; 
    }
    
    public static Object getInfo(){ 
    	return "<H2>SHANKS</H2> Simulation of Heterogeneal and Autonomous Networks"; 
    }
    
    public Object getSimulationInspectedObject() { 
    	return state; 
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
    	if (displayFrame!=null && frameMessage != null){ 
    		displayFrame.dispose();
    		frameMessage.dispose();
    		frameMessage = null;
       		displayFrame = null;
    		display = null;
    		displayMessage = null;
    	}
    }
    
    public static Image loadImage(String filename)
    { 
    return new ImageIcon(Model.class.getResource(filename)).getImage(); 
    }
    
    public void setupPortrayals(){
    	display.destroySceneGraph();
    	Model mod = (Model) state;
    	
//    	displayMessage = new Display2D(600, 600, this, 1);
//		displayMessage.setClipping(false);
//
//		frameMessage = displayMessage.createFrame();
//		displayFrame1.setTitle("Environment Display - Simulation Model 1");
//		c.registerFrame(displayFrame1);
//		displayFrame1.setVisible(true);
    	
    	edgePortrayal.setField( new SpatialNetwork3D(mod.elements3d, mod.links1 ) );
        SimpleEdgePortrayal3D linkportrayal = new CylinderEdgePortrayal3D((float) 1);

        edgePortrayal.setPortrayalForAll(linkportrayal);
        elementsPortrayal.setField(mod.elements3d); 
        DevicePortrayal dport = new DevicePortrayal();
        elementsPortrayal.setPortrayalForAll(dport);
        display.reset();
        display.createSceneGraph();
        
    }
    
    public void init(Controller c){
    	super.init(c);
    	Model mod = (Model) state;
       	display = new Display3D(800,800,this,1);
       	display.attach(edgePortrayal, "EDGES");
    	display.attach(elementsPortrayal, "SHANKS");
    	display.scale(1.0/mod.gridHeight*1.05);
    	displayFrame = display.createFrame();
        c.registerFrame(displayFrame);
        displayFrame.setVisible(true);
        display.mSelectBehavior.setTolerance(10.0f);
        }
    
    
    public class ScenarioChoice{
    	int cells = 0;
    	int error = 0;
    	
    	public Object domScenarioChoice() { 
    		return new Object[] { "REAL FTTH", "SIMPLE FTTH"}; 
    	}
    	public Object domErrorGenerationChoice() { 
    		return new Object[] { "ErrorList", "ProbBroken"}; 
    	}
    	public int getScenarioChoice() {
    		return cells; 
    	}
    	public int getErrorGenerationChoice(){
    		return error;
    	}
    	public void setErrorGenerationChoice(int val)
        {
        if (val == 0)
            {
            error = val;
            Model.SELECT_ERROR_GENERATION = "ErrorList";
            }
        else if (val == 1)
            {
            error = val;
            Model.SELECT_ERROR_GENERATION = "ProbBroken";
            }
        }
    	public void setScenarioChoice(int val)
        {
        if (val == 0)
            {
            cells = val;
            Model.SELECT_SCENARIO = "REAL FTTH";
            }
        else if (val == 1)
            {
            cells = val;
            Model.SELECT_SCENARIO = "SIMPLE FTTH";
            }
            
        // reattach the portrayals
        display.detatchAll();
        display.attach(elementsPortrayal,"Heat");
        display.attach(edgePortrayal,"Bugs");

        // redisplay
        if (display!=null) display.repaint();
        }
    }
	public Inspector getInspector() {
		
		log.fine("-> Inspector");
		
		final Inspector originalInspector = super.getInspector();
		final SimpleInspector scenarioInspector = new SimpleInspector(new ScenarioChoice(),this);
		
		originalInspector.setVolatile(true);
		
		Inspector newInspector = new Inspector(){
			private static final long serialVersionUID = -8213271730234903099L;
			public void updateInspector(){ 
				originalInspector.updateInspector(); 
			}
        };
        
        newInspector.setVolatile(false);
		
		
		Box b = new Box(BoxLayout.X_AXIS) {
        /**
			 * 
			 */
			private static final long serialVersionUID = 7429894079728338277L;

		public Insets getInsets(){ 
        	return new Insets(2,2,2,2); 
        	}
        };
        
        b.add(newInspector.makeUpdateButton());
        b.add(Box.createGlue());
        
        log.info("Before Box b2");
        
        Box b2 = new Box(BoxLayout.Y_AXIS);
        b2.add(b);
        b2.add(scenarioInspector);
        b2.add(Box.createGlue());
    

        newInspector.setLayout(new BorderLayout());
        newInspector.add(b2,BorderLayout.NORTH);
        newInspector.add(originalInspector,BorderLayout.CENTER);

        log.fine("Inspector ->");
        
        return newInspector;
    }
    

	}

