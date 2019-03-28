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
package es.upm.dit.gsi.shanks.model.scenario.portrayal;

import java.util.HashMap;

import org.jfree.data.general.Series;
import org.jfree.data.xy.XYSeries;

import sim.field.network.Network;
import sim.portrayal.Portrayal;
import sim.util.media.chart.ChartGenerator;
import sim.util.media.chart.HistogramGenerator;
import sim.util.media.chart.ScatterPlotGenerator;
import sim.util.media.chart.TimeSeriesChartGenerator;
import es.upm.dit.gsi.shanks.exception.ShanksException;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedChartIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedDataSerieIDException;
import es.upm.dit.gsi.shanks.model.scenario.portrayal.exception.DuplicatedPortrayalIDException;

public abstract class ScenarioPortrayal {
    
    private Scenario scenario;
    private HashMap<String,HashMap<String, Portrayal>> portrayals; // <displayID - <portrayalID, portrayal>>
    private HashMap<String,TimeSeriesChartGenerator> timeCharts;
    private HashMap<String, HashMap<String, XYSeries>> timeChartData;
    
    private HashMap<String, ScatterPlotGenerator> scatterPlots;
    private HashMap<String, double[][]> scatterPlotsData;
    
    private HashMap<String, HistogramGenerator> histograms;
    
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
        this.scatterPlots = new HashMap<String, ScatterPlotGenerator>();
        this.scatterPlotsData = new HashMap<String, double[][]>();
        this.histograms = new HashMap<String, HistogramGenerator>();
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
     * @throws ShanksException 
     */
    public void addPortrayal(String displayID, String portrayalID, Portrayal portrayal) throws ShanksException {
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
     * @param xAxisLabel
     * @param yAxisLabel
     * @throws ShanksException
     */
    public void addTimeChart(String chartID, String xAxisLabel, String yAxisLabel) throws ShanksException {
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
     * @throws ShanksException
     */
    public void addDataSerieToTimeChart(String chartID, String dataSerieID) throws ShanksException {
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
     * @return
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
     * @throws ShanksException 
     */
    public void addDataToDataSerieInTimeChart(String chartID, String dataSerieID, double x, double y) throws ShanksException {
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
    
    /**
     * Add a ScatterPlot to the simulation
     * 
     * @param scatterID - An ID for the ScatterPlot
     * @param xAxisLabel - The name of the x axis
     * @param yAxisLabel - The name of the y axis 
     * @throws ShanksException
     */
    public void addScatterPlot(String scatterID, String xAxisLabel, String yAxisLabel) throws ShanksException {
        if (!this.timeCharts.containsKey(scatterID)) {
            ScatterPlotGenerator scatter = new ScatterPlotGenerator();
            scatter.setTitle(scatterID);
            scatter.setXAxisLabel(xAxisLabel);
            scatter.setYAxisLabel(yAxisLabel);
            this.scatterPlots.put(scatterID, scatter);
        } else {
            throw new DuplicatedChartIDException(scatterID);
        }
    }
    
    /**
     * Remove a ScatterPlot from the simulation
     * 
     * @param scatterID
     */
    public void removeScatterPlot(String scatterID) {
        if (this.scatterPlots.containsKey(scatterID)) this.scatterPlots.remove(scatterID);
    }
    
    /**
     * @return Map with all ScatterPlots of the simulation
     */
    public HashMap<String, ScatterPlotGenerator> getScatterPlots() {
        return this.scatterPlots;
    }
    
    /**
     * @param scatterID
     * @return the scatterPlot if it exists, null if the scatterPlot does not exist
     */
    public ChartGenerator getScatterPlot(String scatterID) {
        if (this.scatterPlots.containsKey(scatterID)) 
            return this.scatterPlots.get(scatterID);
        return null;
    }
    
    /**
     * Sets the data for the scatterPlot.
     * Should be used only the first time. Use 
     * updateDataSerieOnScaterPlot to change the data
     *
     * 
     * @param scatterID
     * @param dataSerie the data. The first dimension MUST BE 2 (double[2][whatever_int])
     */
    public void addDataSerieToScatterPlot(String scatterID, double[][] dataSerie){
        if (this.scatterPlots.containsKey(scatterID)) {
            this.scatterPlotsData.put(scatterID, dataSerie);
            this.scatterPlots.get(scatterID).addSeries((double[][])this.scatterPlotsData.get(scatterID), scatterID, null);
        }
    }
    
    /**
     * Updates the data in the scatterPlot
     * 
     * @param scatterID
     * @param index the data serie to modify
     * @param dataSerie the series data
     */
    public void updateDataSerieOnScatterPlot(String scatterID, int index, double[][] dataSerie){
        if (this.scatterPlots.containsKey(scatterID)){
            if (this.scatterPlotsData.containsKey(scatterID)) {
                double[][] current = this.scatterPlotsData.get(scatterID);
                this.scatterPlotsData.put(scatterID, concatenateArrays(current, dataSerie));
            } else{
                // Create the data.
                addDataSerieToScatterPlot(scatterID, dataSerie);
                return;
            }
            this.scatterPlots.get(scatterID).updateSeries(index, this.scatterPlotsData.get(scatterID));
//            this.scatterPlots.get(scatterID).addSeries(this.scatterPlotsData.get(scatterID), scatterID, null);
        }
    }
    
    /**
     * Add a histogram to the simulation
     * 
     * @param histogramID
     * @param xAxisLabel
     * @param yAxisLabel
     * @throws ShanksException 
     */
    public void addHistogram(String histogramID, String xAxisLabel, String yAxisLabel) throws ShanksException {
        if (!this.histograms.containsKey(histogramID)) {
            HistogramGenerator histogram = new HistogramGenerator();
            histogram.setTitle(histogramID);
            histogram.setXAxisLabel(xAxisLabel);
            histogram.setYAxisLabel(yAxisLabel);
            this.histograms.put(histogramID, histogram);
        } else {
            throw new DuplicatedChartIDException(histogramID);
        }
    }
    
    /**
     * Remove a histogram from the simulation
     * 
     * @param histogramID
     */
    public void removeHistogram(String histogramID) {
        if (this.histograms.containsKey(histogramID)) {
            this.histograms.remove(histogramID);
        }
    }
    
    /**
     * @return Map with all histograms of the simulation
     */
    public HashMap<String, HistogramGenerator> getHistograms() {
        return this.histograms;
    }
    
    /**
     * @param histogramID
     * @return the histogram if it exists, null if it does not
     */
    public HistogramGenerator getHistogram(String histogramID) {
        if (this.histograms.containsKey(histogramID)) {
            return this.histograms.get(histogramID);
        } else {
            return null;
        }
    }
    
    
    /**
     * Adds data to the histogram.
     * Given the number of bins, this will show a count on the number of
     * times a value is repeated. For example:
     * [1,2,3,1,1,3], and bins = 3
     * Will provide:
     *   _
     *  | |    _
     *  | | _ | |
     * _|_||_||_|_
     * 
     * If the number of different values is different from the number of bins,
     * then each bin will represent the number of values in a range, evenly distributed
     * between the minimum and the maximum value. For example:
     * [1,2,3,1,1,3], and bins = 2
     * Two ranges will be considered: [1,2) and [2,3].
     * BE ADVISED The middle value would be assigned to the upper range.
     * In this example, the histogram will look:
     *   _  _
     *  | || |
     *  | || |
     * _|_||_|_
     * 
     * 
     * @param histogramID
     * @param dataSerie the data
     * @param binsCount the number of bins
     */
    public void addDataSerieToHistogram(String histogramID, double[] dataSerie, int binsCount){
        if (this.histograms.containsKey(histogramID)) {
            this.histograms.get(histogramID).addSeries(dataSerie, binsCount, histogramID, null);
        }
    }
    /**
     * Set the histogram range to be displayed
     * 
     * @param histogramID The histogram ID
     * @param lower The lower value for the histogram
     * @param upper The upper value for the histogram
     */
    public void setHistogramRange(String histogramID, int lower, int upper) {
        if (this.histograms.containsKey(histogramID))
            this.histograms.get(histogramID).setYAxisRange(lower, upper);
    }
    
    /**
     * Updates the given Histogram (index).
     * The index will be the order of the histogram.
     * 
     * 
     * @param histogramID
     * @param dataSerie
     * @param index
     */
    public void updateDataSerieToHistogram(String histogramID, double[] dataSerie, int index){
        this.histograms.get(histogramID).updateSeries(index, dataSerie);
    }
    
    /**
     * Deletes a certain series of data from the histogram.
     * The index will be the order of the histogram.
     * 
     * @param histogramID
     * @param index - the index of the data
     */
    public void removeDataSerieFromHistogram(String histogramID, int index) {
        if (this.histograms.containsKey(histogramID)) {
            this.histograms.get(histogramID).removeSeries(index);
        }
    }
    
    /**
     * @param histogramID
     * @param index
     * @param dataSerie
     */
    public void updateDataToDataSerieInTimeChart(String histogramID, int index, double[] dataSerie  ) {
        if (this.histograms.containsKey(histogramID)) {
            this.histograms.get(histogramID).updateSeries(index, dataSerie);
        }
    }
    
    /**
     * Concatenate two two dimensional arrays.
     * Both arrays must have the same dimensions.
     * 
     * @param first
     * @param second
     * @return
     */
    private double[][] concatenateArrays(double[][] first, double[][] second){
        // I am sure as hell that should be a better way to do this...
        double[][] result = new double[first.length][first[0].length + second[0].length];
        for(int i = 0; i < first.length; i++){
            for (int j = 0; j < (first[i].length + second[i].length); j++){
                if( j < first[i].length){
                    result[i][j] = first[i][j];
                } else{
                    result[i][j] = second[i][j - first[i].length];
                }
            }
        }
        return result;
    }
}
