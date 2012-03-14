package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import org.jfree.data.general.Series;
import org.jfree.data.xy.XYSeries;

import sim.field.network.Network;
import sim.portrayal.Portrayal;
import sim.util.media.chart.ChartGenerator;
import sim.util.media.chart.TimeSeriesChartGenerator;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedDataSerieIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public abstract class ScenarioPortrayal {
    
    private Scenario scenario;
    private HashMap<String,HashMap<String, Portrayal>> portrayals; // <displayID - <portrayalID, portrayal>>
    private HashMap<String,TimeSeriesChartGenerator> timeCharts;
    private HashMap<String, HashMap<String, XYSeries>> timeChartData;
    
    public static final String DEVICES_PORTRAYAL = "Devices";
    public static final String LINKS_PORTRAYAL = "Links";
    
    /**
     * @param scenario
     */
    public ScenarioPortrayal(Scenario scenario) {
        this.scenario = scenario;
        this.portrayals = new HashMap<String,HashMap<String, Portrayal>>();
        this.timeCharts = new HashMap<String, TimeSeriesChartGenerator>();
        this.timeChartData = new HashMap<String, HashMap<String,XYSeries>>();
    }
    
    /**
     * @return Scenario of this portrayal
     */
    public Scenario getScenario() {
        return this.scenario;
    }
    
    /**
     * @return the SparseGrid2D or the Continuous3D object
     */
    abstract public Object getPlacedDevices();
    
    /**
     * @return the Network object with all links
     */
    abstract public Network getLinks();
    
    /**
     * To setup all portrayals of the simulation
     */
    abstract public void setupPortrayals();

    /**
     * @return the portrayals
     */
    public HashMap<String,HashMap<String, Portrayal>> getPortrayals() {
        return portrayals;
    }

    /**
     * @param portrayals the portrayals to set
     */
    public void setPortrayals(HashMap<String,HashMap<String, Portrayal>> portrayals) {
        this.portrayals = portrayals;
    }
    
    /**
     * @param displayID
     * @param portrayalID
     * @param portrayal
     * @throws DuplicatedPortrayalIDException 
     */
    public void addPortrayal(String displayID, String portrayalID, Portrayal portrayal) throws DuplicatedPortrayalIDException {
        if (!this.portrayals.containsKey(displayID)) {
            this.portrayals.put(displayID, new HashMap<String,Portrayal>());
        }
        HashMap<String,Portrayal> displayPortrayals = this.portrayals.get(displayID);
        if (!displayPortrayals.containsKey(portrayalID)) {
            displayPortrayals.put(portrayalID, portrayal);
        } else {
            throw new DuplicatedPortrayalIDException(displayID,portrayalID);
        }
    }
    
    /**
     * @param displayID
     * @param portrayalID
     */
    public void removePortrayal(String displayID, String portrayalID) {
        if (this.portrayals.containsKey(displayID)) {
            this.portrayals.get(displayID).remove(portrayalID);   
        }
    }
    
    /**
     * Add a chart to the simulation
     * 
     * @param chartID
     * @param chart
     * @throws DuplicatedChartIDException 
     */
    public void addTimeChart(String chartID, String xAxisLabel, String yAxisLabel) throws DuplicatedChartIDException {
        if (!this.timeCharts.containsKey(chartID)) {
            TimeSeriesChartGenerator chart = new TimeSeriesChartGenerator();
            chart.setTitle(chartID);
            chart.setXAxisLabel(xAxisLabel);
            chart.setYAxisLabel(yAxisLabel);
            this.timeCharts.put(chartID, chart);
        } else {
            throw new DuplicatedChartIDException(chartID);
        }
    }
    
    /**
     * Remove a chart to the simulation
     * 
     * @param chartID
     */
    public void removeTimeChart(String chartID) {
        if (this.timeCharts.containsKey(chartID)) {
            this.timeCharts.remove(chartID);
        }
    }
    
    /**
     * @return Map with all charts of the simulation
     */
    public HashMap<String, TimeSeriesChartGenerator> getTimeCharts() {
        return this.timeCharts;
    }
    
    /**
     * @param chartID
     * @return the chart if it exists, null if the chat does not exist
     */
    public ChartGenerator getTimeChart(String chartID) {
        if (this.timeCharts.containsKey(chartID)) {
            return this.timeCharts.get(chartID);
        } else {
            return null;
        }
    }
    
    /**
     * @param chartID
     * @param dataSerieID
     * @param dataSerie
     * @throws DuplicatedDataSerieIDException
     */
    public void addDataSerieToTimeChart(String chartID, String dataSerieID) throws DuplicatedDataSerieIDException {
        if (!this.timeChartData.containsKey(chartID)) {
            this.timeChartData.put(chartID, new HashMap<String, XYSeries>());
        }
        HashMap<String, XYSeries> dataSeries = this.timeChartData.get(chartID);
        if (!dataSeries.containsKey(dataSerieID)) {
            XYSeries serie = new XYSeries(dataSerieID, false);
            dataSeries.put(dataSerieID, serie);
            TimeSeriesChartGenerator chart =  (TimeSeriesChartGenerator) this.timeCharts.get(chartID);
            chart.addSeries(serie, null);
        } else {
            throw new DuplicatedDataSerieIDException(chartID, dataSerieID);
        }
    }
    
    /**
     * @param chartID
     * @param dataSerieID
     * @param dataSerie
     * @throws DuplicatedDataSerieIDException
     */
    public Series getDataSerieFromTimeChart(String chartID, String dataSerieID) {
        return this.timeChartData.get(chartID).get(dataSerieID);
    }
    
    /**
     * @param chartID
     * @param dataSerieID
     */
    public void removeDataSerieToTimeChart(String chartID, String dataSerieID) {
        if (this.timeChartData.containsKey(chartID) && this.timeChartData.get(chartID).containsKey(dataSerieID)) {
            this.timeChartData.get(chartID).remove(dataSerieID);
        }
    }
    
    /**
     * @param chartID
     * @param dataSerieID
     * @return
     */
    public boolean containsDataSerieInTimeChart(String chartID, String dataSerieID) {
        if (this.timeChartData.containsKey(chartID) && this.timeChartData.get(chartID).containsKey(dataSerieID)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Add a datum in the chart
     * 
     * @param chartID
     * @param dataSerieID
     * @param x
     * @param y
     */
    public void addDataToDataSerieInTimeChart(String chartID, String dataSerieID, double x, double y) {
        if (this.containsDataSerieInTimeChart(chartID, dataSerieID)) {
            this.timeChartData.get(chartID).get(dataSerieID).add(x, y, true);
        } else {
            try {
                this.addDataSerieToTimeChart(chartID, dataSerieID);
                this.timeChartData.get(chartID).get(dataSerieID).add(x, y, true);
            } catch (DuplicatedDataSerieIDException e) {
                e.printStackTrace();
            }
        }
    }

}
