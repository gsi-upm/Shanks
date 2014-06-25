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
package es.upm.dit.gsi.shanks.model.test;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

import sim.display3d.Display3D;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.ShanksSimulation3DGUI;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.Scenario3DPortrayal;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.test.MyHyperComplexScenario2DPortrayal;
import es.upm.dit.gsi.shanks.model.test.steppable.FailuresChartPainter;

/**
 * @author a.carrera
 * 
 */
public class MyShanksSimulation3DGUI extends ShanksSimulation3DGUI {

    /**
     * @param sim
     */
    public MyShanksSimulation3DGUI(ShanksSimulation sim) {
        super(sim);
    }

    /**
     * @return
     */
    public static String getName() {
        return "MyShanksSimulation3DGUI! :)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation2DGUI#addDisplays(es.upm.dit.gsi
     * .shanks.model.scenario.portrayal.Scenario2DPortrayal)
     */
    public void addDisplays(Scenario3DPortrayal scenarioPortrayal) {

        Display3D failureDisplay = new Display3D(400, 400, this);
        failureDisplay.scale(0.005);
        failureDisplay.setBackdrop(Color.white);
        try {
            this.addDisplay(
                    MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID,
                    failureDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation3DGUI#addCharts(es.upm.dit.gsi.
     * shanks.model.scenario.portrayal.Scenario3DPortrayal)
     */
    @Override
    public void addCharts(Scenario3DPortrayal scenarioPortrayal)
            throws ShanksException {
        this.addTimeChart(
                FailuresChartPainter.RESOLVED_FAILURES_PER_AGENT_CHART_ID,
                "Time / Steps", "Resolved failures");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.upm.dit.gsi.shanks.ShanksSimulation3DGUI#locateFrames(es.upm.dit.gsi
     * .shanks.model.scenario.portrayal.Scenario3DPortrayal)
     */
    @Override
    public void locateFrames(Scenario3DPortrayal scenarioPortrayal) {
        HashMap<String, JFrame> frames = scenarioPortrayal.getFrameList();
        JFrame mainFrame = frames.get(Scenario2DPortrayal.MAIN_DISPLAY_ID);
        JFrame failureFrame = frames
                .get(MyHyperComplexScenario2DPortrayal.FAILURE_DISPLAY_ID);
        JFrame chartFrame = frames
                .get(FailuresChartPainter.RESOLVED_FAILURES_PER_AGENT_CHART_ID);

        mainFrame.setLocation(100, 100);
        failureFrame.setLocation(500, 0);
        chartFrame.setLocation(600, 200);
    }

}
