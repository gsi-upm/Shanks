/*******************************************************************************
 * Copyright  (C) 2014 Álvaro Carrera Barroso
 * Grupo de Sistemas Inteligentes - Universidad Politecnica de Madrid
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.portrayal;

import java.awt.Color;
import java.awt.Font;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import sim.portrayal3d.SimplePortrayal3D;

import com.sun.j3d.utils.geometry.Sphere;

import es.upm.dit.gsi.shanks.agent.ShanksAgent;

/**
 * Default 3D protrayal interface for a Shanks Agent. 
 * 
 * @author darofar
 *
 */
@SuppressWarnings("restriction")
public abstract class ShanksAgent3DPortrayal extends SimplePortrayal3D {


    private int diameter = 50;

    public String getLabel(ShanksAgent agent) {
        return agent.getID();
    }
    
    /*
     * (non-Javadoc)
     * @see sim.portrayal3d.SimplePortrayal3D#getModel(java.lang.Object, javax.media.j3d.TransformGroup)
     */
    @Override
    public TransformGroup getModel(Object object, TransformGroup model) {

        ShanksAgent agent = (ShanksAgent) object;
        model = new TransformGroup();

        Color3f colour;
        Sphere s = new Sphere(diameter);
//        Cone s = new Cone(diameter, diameter);
//        ColorCube s = new ColorCube(diameter);
        
        colour = new Color3f(this.getAgentColor(agent));

        int FONT_SIZE = 48;
        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);
        double labelScale = 1.0;
        double SCALING_MODIFIER = 1.0 / 5.0; 

        model.removeAllChildren();
        Appearance appearance = new Appearance();
        appearance.setColoringAttributes(new ColoringAttributes(colour,
                ColoringAttributes.SHADE_GOURAUD));
        Material m = new Material();
        m.setAmbientColor(colour);
        m.setEmissiveColor(0f, 0f, 0f);
        m.setDiffuseColor(colour);
        m.setSpecularColor(1f, 1f, 1f);
        m.setShininess(128f);
        appearance.setMaterial(m);
        s.setAppearance(appearance);
        model.addChild(s);
        model.setName(agent.getID());
        clearPickableFlags(model);
        model.setPickable(true);
        
        


        // draw the Agent labels if the user wants
        Transform3D offset = new Transform3D();
        offset.setTranslation(new Vector3d(5+diameter/2,5+diameter/2,5+diameter/2));
        Transform3D trans = offset;
        String str = agent.getID();
        com.sun.j3d.utils.geometry.Text2D text = new com.sun.j3d.utils.geometry.Text2D(
                str, new Color3f(this.getLabelColor(agent)), labelFont.getFamily(),
                labelFont.getSize(), labelFont.getStyle());
        text.setRectangleScaleFactor((float) (labelScale * SCALING_MODIFIER));

        // text = new Shape3D(new Text3D(labelFont3D, ""));

        OrientedShape3D o3d = new OrientedShape3D(text.getGeometry(),
                text.getAppearance(), OrientedShape3D.ROTATE_ABOUT_POINT,
                new Point3f(0, 0, 0));
        o3d.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE); // may need to change
                                                           // the appearance
                                                           // (see below)
        o3d.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE); // may need to change
                                                         // the geometry (see
                                                         // below)
        o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_APPEARANCE_WRITE);
        o3d.clearCapabilityIsFrequent(Shape3D.ALLOW_GEOMETRY_WRITE);

        // make the offset TransformGroup
        TransformGroup o = new TransformGroup();
        o.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        o.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        o.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        o.clearCapabilityIsFrequent(TransformGroup.ALLOW_CHILDREN_READ);
        o.setTransform(trans);
        o.setUserData(str);

        // the label shouldn't be pickable -- we'll turn this off in the
        // TransformGroup
        clearPickableFlags(o);
        o.addChild(o3d); // Add label to the offset TransformGroup
        model.addChild(o);

        return model;
    }

    abstract public Color getAgentColor(ShanksAgent agent);

    abstract public Color getLabelColor(ShanksAgent agent);

    private static final long serialVersionUID = 6319038986129405512L;
}