package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SpatialNetwork2D;

/**
 * ModelGUI class
 * 
 * This class create the graphic interface
 * 
 * @author Daniel Lara
 * @version 0.1 
 *
 */

//The GUI of the model
public class Simulation2DGUI extends GUIState {
	
	public Logger log = Logger.getLogger("ModelGUI");
	
	public Display2D display;
	public JFrame frame;
	SparseGridPortrayal2D agents = new SparseGridPortrayal2D();
	NetworkPortrayal2D links = new NetworkPortrayal2D();
	SparseGridPortrayal2D problems = new SparseGridPortrayal2D();



	public static void main(String[] args) {
		Simulation2DGUI modelgui = new Simulation2DGUI();
		modelgui.createController();

	}
	
	public static Image loadImage(String img){ 
    return new ImageIcon(Simulation.class.getResource(img)).getImage(); 
    }


	public Simulation2DGUI() {
		super(new Simulation(System.currentTimeMillis()));
		log.finer("-> Constructor ModelGUI");
	}

	public Simulation2DGUI(SimState state) {
		super(state);
	}

	public static String getName() {
		return "SHANKS Console Control";
	}

	public static Object getInfo() {
		return "<H2>Simulation of Heterogeneal and Autonomus</H2>";
	}

	public Object getSimulationInspectedObject() {
		return state;
	}
	
	public class ScenarioChoice{
		int selected = 0;
		public Object domScenarios(){ 
			return new Object[] { "FTTH", "PRUEBA"}; 
		}
		public int getSelectScenario(){ 
			return selected;
		}
		public void setSelectscenario(int val){
			if(val == 0){
				selected = val;
				Simulation.SELECT_SCENARIO = "FTTH";
			}
			else if (val==1){
				selected = val;
				Simulation.SELECT_SCENARIO = "PRUEBA";
			}
            
        //reattach the portrayals
        display.detatchAll();
        display.attach(agents,"Devices");
        display.attach(links, "Links");

        // redisplay
        if (display!=null) display.repaint();
        }
	}
	
	/**
	 * Creates and returns a controller ready for the user to manipulate. By
	 * default this method creates a Console, sets it visible, and returns it.
	 * You can override this to provide some other kind of controller.
	 */
	public Controller createController() {
		Console console = new Console(this);
		console.setVisible(true);
		return console;
	}
//	public Inspector getInspector() {
//		
//		log.fine("-> Inspector");
//		
//		final Inspector originalInspector = super.getInspector();
//		final SimpleInspector scenarioInspector = new SimpleInspector(new ScenarioChoice(),this);
//		
//		originalInspector.setVolatile(true);
//		
//		Inspector newInspector = new Inspector(){
//			public void updateInspector(){
//				originalInspector.updateInspector();
//			}
//		};
//		
//		newInspector.setVolatile(false);
//		
//		Box b = new Box(BoxLayout.X_AXIS) {
//        public Insets getInsets(){ 
//        	return new Insets(2,2,2,2); 
//        	}  // in a bit
//        };
//        
//        b.add(newInspector.makeUpdateButton());
//        b.add(Box.createGlue());
//        
//        log.info("Before Box b2");
//        
//        Box b2 = new Box(BoxLayout.Y_AXIS);
//        b2.add(b);
//        b2.add(scenarioInspector);
//        b2.add(Box.createGlue());
//    
//    // all one blob now.  We can add it at NORTH.
//
//        newInspector.setLayout(new BorderLayout());
//        newInspector.add(b2,BorderLayout.NORTH);
//        newInspector.add(originalInspector,BorderLayout.CENTER);
//
//        log.fine("Inspector ->");
//        
//        return originalInspector;
//    }
//	
//	
//	
	

	public void start() {
		super.start();
		setupPortrayals(); 
	}
	
	//Setup the Portrayals
	public void setupPortrayals(){
		Simulation model = (Simulation) state;
		agents.setField(Simulation.problems);
		links.setField(new SpatialNetwork2D(Simulation.problems, model.links1));
		//links.setPortrayalForAll(new Links());
		display.reset();
		display.setBackdrop(Color.white);
		display.repaint();
	}
	
	//Controller that create the displays
	 public void init(Controller c){
		 super.init(c);
		 display = new Display2D(600, 600, this, 1);
		 display.setClipping(false);
		 frame = display.createFrame();
		 frame.setTitle("Agent Simulation");
		 c.registerFrame(frame);
		 frame.setVisible(true);
		 display.attach(links, "Links");
		 display.attach(agents, "Agens");
		 
     }
	 
	 
	 
	 public void quit(){
		 super.quit();
     
		 if (frame!=null){
			frame.dispose();
		 	frame = null; 
		 	display = null;
		 }
     }


}

		