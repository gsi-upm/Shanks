package es.upm.dit.gsi.shanks;

import java.awt.Color;
import java.awt.Image;
import java.util.logging.Logger;

import javax.media.j3d.TransformGroup;
import javax.swing.ImageIcon;

import sim.portrayal3d.simple.BoxPortrayal3D;
import sim.portrayal3d.simple.CubePortrayal3D;
import sim.portrayal3d.simple.SpherePortrayal3D;


public class DevicePortrayal extends BoxPortrayal3D{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8701221342283565341L;

	public Logger log = Logger.getLogger("DevicePortrayal");
	
	final static Color healthyColor = Color.green;
    final static Color brokenColor = Color.red;
    public float diameter = 40;
        
    public DevicePortrayal(){
    }
    
//    public SpherePortrayal3D getSphere(Object obj){
//    	SpherePortrayal3D portrayal = null;
//    		if(((Device)obj).getStatus() == Definitions.HEALTHY_STATUS){
//    			portrayal = new SpherePortrayal3D(Model3DGUI.loadImage("earthmap.jpg");
//    		}
//		return portrayal;
//    	
//    }
    
    public TransformGroup getModel(Object obj, TransformGroup j3dModel){
    		if (j3dModel==null || ((Device)obj).status == Definitions.HEALTHY_STATUS){
    			setAppearance(j3dModel, appearanceForColors(
                        healthyColor,
                        null, 
                        healthyColor, 
                        null,
                        1.0f,
                        1.0f));
    		}else{ 
    			setAppearance(j3dModel, appearanceForColors(
                        brokenColor, 
                        null,
                        brokenColor, 
                        null,    
                        1.0f, 
                        1.0f));
            }
    		if(j3dModel == null){
    			setScale(j3dModel, diameter);
    		}
        return super.getModel(obj, j3dModel);
    }
    
//    public TransformGroup getModel(Object obj, TransformGroup j3dModel){
//		if (((Device)obj).getType() == Definitions.OLT){
//			setAppearance(j3dModel, appearanceForImage(Model3DGUI.loadImage("OLT(Recortada).jpg"), true));
////		}else{ 
////			setAppearance(j3dModel, appearanceForImage(Model3DGUI.loadImage("OLT(Recortada).jpg"), true));
//        }else if(((Device)obj).getStatus() == Definitions.HEALTHY_STATUS && ((Device)obj).getType() != Definitions.OLT)
//        	setAppearance(j3dModel, appearanceForColors(
//                  brokenColor, 
//                  null,
//                  brokenColor, 
//                  null,    
//                  1.0f, 
//                  1.0f));
//    
//        	if(j3dModel == null){
//			setScale(j3dModel, diameter);
//		}
//    return super.getModel(obj, j3dModel);
//}
    
    

	
}
