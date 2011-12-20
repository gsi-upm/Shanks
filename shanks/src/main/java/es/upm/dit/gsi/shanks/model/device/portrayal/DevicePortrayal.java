package es.upm.dit.gsi.shanks.model.device.portrayal;

import java.util.logging.Logger;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import sim.portrayal3d.SimplePortrayal3D;
import sim.portrayal3d.network.SimpleEdgePortrayal3D;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

import es.upm.dit.gsi.shanks.model.device.Device;
import es.upm.dit.gsi.shanks.model.scenario.ScenarioDefinitions;

public class DevicePortrayal extends SimplePortrayal3D {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8701221342283565341L;

    public Logger log = Logger.getLogger("DevicePortrayal");

    final static Color3f healthyColor = new Color3f(0, 200, 0);
    final static Color3f brokenColor = new Color3f(200, 0, 0);
    public float diameter = 30;

    public DevicePortrayal() {

    }

    public String getLabel(es.upm.dit.gsi.shanks.model.device.Device d) {
        String deviceName = d.getID();
        return deviceName;
    }

    @Override
    public TransformGroup getModel(Object obj, TransformGroup j3dModel) {
        if (j3dModel == null || ((Device) obj).getType() == ScenarioDefinitions.GATEWAY) {
            j3dModel = new TransformGroup();
            Sphere s = new Sphere(diameter);
            if (((Device) obj).getStatus() == ScenarioDefinitions.HEALTHY_STATUS) {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        healthyColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(healthyColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(healthyColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);

                clearPickableFlags(j3dModel);
            } else {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        brokenColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(brokenColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(brokenColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            }
        } else if (j3dModel == null
                || ((Device) obj).getType() == ScenarioDefinitions.SPLITTER1) {
            j3dModel = new TransformGroup();
            Appearance appearance = new Appearance();
            Box s = new Box(diameter, diameter * 3, diameter, appearance);
            if (((Device) obj).getStatus() == ScenarioDefinitions.HEALTHY_STATUS) {
                j3dModel.removeAllChildren();
                appearance.setColoringAttributes(new ColoringAttributes(
                        healthyColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(healthyColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(healthyColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            } else {
                j3dModel.removeAllChildren();
                appearance.setColoringAttributes(new ColoringAttributes(
                        brokenColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(brokenColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(brokenColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            }

        } else if (j3dModel == null
                || ((Device) obj).getType() == ScenarioDefinitions.SPLITTER2) {
            j3dModel = new TransformGroup();
            Appearance appearance = new Appearance();
            Box s = new Box(diameter, diameter, diameter, appearance);
            if (((Device) obj).getStatus() == ScenarioDefinitions.HEALTHY_STATUS) {
                j3dModel.removeAllChildren();
                appearance.setColoringAttributes(new ColoringAttributes(
                        healthyColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(healthyColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(healthyColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            } else {
                j3dModel.removeAllChildren();
                appearance.setColoringAttributes(new ColoringAttributes(
                        brokenColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(brokenColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(brokenColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            }
        } else if (j3dModel == null
                || ((Device) obj).getType() == ScenarioDefinitions.OLT) {
            j3dModel = new TransformGroup();
            Cone s = new Cone(diameter, diameter * 3);
            if (((Device) obj).getStatus() == ScenarioDefinitions.HEALTHY_STATUS) {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        healthyColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(healthyColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(healthyColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            } else {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        brokenColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(brokenColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(brokenColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            }
        } else if (j3dModel == null
                || ((Device) obj).getType() == ScenarioDefinitions.ONT) {
            j3dModel = new TransformGroup();
            Cylinder s = new Cylinder(diameter, diameter);
            if (((Device) obj).getStatus() == ScenarioDefinitions.HEALTHY_STATUS) {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        healthyColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(healthyColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(healthyColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            } else {
                j3dModel.removeAllChildren();
                Appearance appearance = new Appearance();
                appearance.setColoringAttributes(new ColoringAttributes(
                        brokenColor, ColoringAttributes.SHADE_GOURAUD));
                Material m = new Material();
                m.setAmbientColor(brokenColor);
                m.setEmissiveColor(0f, 0f, 0f);
                m.setDiffuseColor(brokenColor);
                m.setSpecularColor(1f, 1f, 1f);
                m.setShininess(128f);
                appearance.setMaterial(m);
                s.setAppearance(appearance);
                j3dModel.addChild(s);
                clearPickableFlags(j3dModel);
            }
        }
        return super.getModel(obj, j3dModel);
    }

}
