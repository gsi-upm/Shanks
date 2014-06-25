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
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.unbbayes.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknowkNodeStateException;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknownNodeException;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.unbbayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.unbbayes.ShanksAgentBayesianReasoningCapability;

/**
 * @author a.carrera
 * 
 */
public class ShanksAgentBayesianReasoningCapabilityTest {

    ProbabilisticNetwork bn;
    BayesianReasonerShanksAgent agent;
    static Logger logger;
    static long initTime;
    static long endTime;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));
        logger = Logger.getLogger("ShanksAgentBayesianReasoningUnbbayes");
        initTime = System.currentTimeMillis();
        logger.info("Init Time: " + initTime);
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        endTime = System.currentTimeMillis();
        logger.info("End Time: " + endTime);
        logger.info("Execution time in milliseconds: " + (endTime - initTime));
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        bn = ShanksAgentBayesianReasoningCapability
                .loadNetwork("src/test/resources/alarm.net");
        agent = new BayesianReasonerShanksAgent() {
            ProbabilisticNetwork bayes;

            @Override
            public String getBayesianNetworkFilePath() {
                return "src/test/resources/alarm.net";
            }

            @Override
            public ProbabilisticNetwork getBayesianNetwork() {
                return bayes;
            }

            @Override
            public void setBayesianNetwork(ProbabilisticNetwork bn) {
                bayes = bn;
            }
        };
        ShanksAgentBayesianReasoningCapability.loadNetwork(agent);
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryBayesCapabilityTest() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefsInferAndQueryBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(this.bn,
                    evidences);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.4126F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2253F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefsInferAndQueriesBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(this.bn,
                    evidences);

            HashMap<String, List<String>> queries = new HashMap<String, List<String>>();
            // First node to query
            String queryNodeName = "TPR";
            List<String> statesList = new ArrayList<String>();
            String queryStatus = "Normal";
            statesList.add(queryStatus);
            queryStatus = "Low";
            statesList.add(queryStatus);
            queries.put(queryNodeName, statesList);
            // Second node to query
            queryNodeName = "ArtCO2";
            statesList = new ArrayList<String>();
            queryStatus = "Normal";
            statesList.add(queryStatus);
            queries.put(queryNodeName, statesList);

            HashMap<String, HashMap<String, Float>> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getHypotheses(this.bn, queries);
            float value = queriesResult.get("TPR").get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("TPR").get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("ArtCO2").get("Normal");
            Assert.assertEquals(0.2253F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void GetNodeHypothesesBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(this.bn,
                    evidences);

            // First node to query
            String queryNodeName = "TPR";
            HashMap<String, Float> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getNodeStatesHypotheses(this.bn, queryNodeName);
            float value = queriesResult.get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("High");
            Assert.assertEquals(0.3043, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void GetAllHypothesesBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(this.bn,
                    evidences);

            HashMap<String, HashMap<String, Float>> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getAllHypotheses(this.bn);
            float value = queriesResult.get("TPR").get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("TPR").get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("ArtCO2").get("Normal");
            Assert.assertEquals(0.2253F, value, 0.001F);
            value = queriesResult.get("SaO2").get("Normal");
            Assert.assertEquals(0.1716F, value, 0.001F);
            value = queriesResult.get("CVP").get("Normal");
            Assert.assertEquals(0.6868F, value, 0.001F);
            value = queriesResult.get("MinVol").get("High");
            Assert.assertEquals(1.0F, value, 0.001F);
            value = queriesResult.get("MinVol").get("Low");
            Assert.assertEquals(0.0F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryBayesCapabilityTestFail1() {
        try {
            String nodeName = "TESTTOFAIL";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryBayesCapabilityTestFail2() {
        try {
            String nodeName = "MinVol";
            String status = "TESTTOFAIL";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            Assert.assertTrue(true);
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryBayesCapabilityTestFail3() {
        try {
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn, null,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAgentBayesCapabilityTest() {
        try {

            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(agent, nodeName,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefsInferAndQueryAgentBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(agent,
                    evidences);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.4126F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2253F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefsInferAndQueriesAgentBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(agent,
                    evidences);

            HashMap<String, List<String>> queries = new HashMap<String, List<String>>();
            // First node to query
            String queryNodeName = "TPR";
            List<String> statesList = new ArrayList<String>();
            String queryStatus = "Normal";
            statesList.add(queryStatus);
            queryStatus = "Low";
            statesList.add(queryStatus);
            queries.put(queryNodeName, statesList);
            // Second node to query
            queryNodeName = "ArtCO2";
            statesList = new ArrayList<String>();
            queryStatus = "Normal";
            statesList.add(queryStatus);
            queries.put(queryNodeName, statesList);

            HashMap<String, HashMap<String, Float>> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getHypotheses(agent, queries);
            float value = queriesResult.get("TPR").get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("TPR").get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("ArtCO2").get("Normal");
            Assert.assertEquals(0.2253F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void GetNodeHypothesesAgentBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(agent,
                    evidences);

            // First node to query
            String queryNodeName = "TPR";
            HashMap<String, Float> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getNodeStatesHypotheses(agent, queryNodeName);
            float value = queriesResult.get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("High");
            Assert.assertEquals(0.3043, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void GetAllHypothesesAgentBayesCapabilityTest() {
        try {
            HashMap<String, String> evidences = new HashMap<String, String>();
            // First evidence
            String nodeName = "MinVol";
            String status = "High";
            evidences.put(nodeName, status);
            // Second evidence
            nodeName = "HRSat";
            status = "Low";
            evidences.put(nodeName, status);
            ShanksAgentBayesianReasoningCapability.addEvidences(agent,
                    evidences);

            HashMap<String, HashMap<String, Float>> queriesResult = ShanksAgentBayesianReasoningCapability
                    .getAllHypotheses(agent);
            float value = queriesResult.get("TPR").get("Normal");
            Assert.assertEquals(0.4126F, value, 0.001F);
            value = queriesResult.get("TPR").get("Low");
            Assert.assertEquals(0.2831F, value, 0.001F);
            value = queriesResult.get("ArtCO2").get("Normal");
            Assert.assertEquals(0.2253F, value, 0.001F);
            value = queriesResult.get("SaO2").get("Normal");
            Assert.assertEquals(0.1716F, value, 0.001F);
            value = queriesResult.get("CVP").get("Normal");
            Assert.assertEquals(0.6868F, value, 0.001F);
            value = queriesResult.get("MinVol").get("High");
            Assert.assertEquals(1.0F, value, 0.001F);
            value = queriesResult.get("MinVol").get("Low");
            Assert.assertEquals(0.0F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAgentBayesCapabilityTestFail1() {
        try {
            String nodeName = "TESTTOFAIL";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(agent, nodeName,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAgentBayesCapabilityTestFail2() {
        try {
            String nodeName = "MinVol";
            String status = "TESTTOFAIL";
            ShanksAgentBayesianReasoningCapability.addEvidence(agent, nodeName,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            Assert.assertTrue(true);
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAgentBayesCapabilityTestFail3() {
        try {
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(agent, null,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAndResetEvidencesBayesCapabilityTest() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
            ShanksAgentBayesianReasoningCapability.clearEvidences(this.bn);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.6817F, value, 0.001F);
            queryNodeName = "MinVol";
            queryStatus = "High";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.0432F, value, 0.001F);
            queryNodeName = "TPR";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAndResetEvidencesAgentBayesCapabilityTest() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(agent, nodeName,
                    status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    agent, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
            ShanksAgentBayesianReasoningCapability.clearEvidences(agent);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.6817F, value, 0.001F);
            queryNodeName = "TPR";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(agent,
                    queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAndResetEvidencesAndAddFindingAgainBayesCapabilityTest() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.2322F, value, 0.001F);
            ShanksAgentBayesianReasoningCapability.clearEvidences(this.bn);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.6817F, value, 0.001F);
            queryNodeName = "MinVol";
            queryStatus = "High";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.0432F, value, 0.001F);
            queryNodeName = "TPR";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);

            nodeName = "MinVol";
            status = "Low";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            queryNodeName = "ArtCO2";
            queryStatus = "High";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.8434F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.1274, value, 0.001F);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void CheckNodeCPT() {
        try {
            String auxNodeName = "History";

            ProbabilisticNode node = ShanksAgentBayesianReasoningCapability
                    .getNode(this.bn, auxNodeName);
            PotentialTable cpt = node.getProbabilityFunction();
            Assert.assertEquals(4, cpt.tableSize());
            Assert.assertEquals(0.9F, cpt.getValue(0));
            Assert.assertEquals(0.1F, cpt.getValue(1));
            Assert.assertEquals(0.01F, cpt.getValue(2));
            Assert.assertEquals(0.99F, cpt.getValue(3));
        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidence() {
        try {
            String targetNodeName = "History";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.70);
            softEvidence.put("False", 0.30);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            double trueConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "True");
            double falseConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "False");

            Assert.assertEquals(0.7, trueConf, 0.01);
            Assert.assertEquals(0.3, falseConf, 0.01);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidence2Times() {
        try {
            String targetNodeName = "History";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.70);
            softEvidence.put("False", 0.30);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            double trueConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "True");
            double falseConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "False");

            Assert.assertEquals(0.7, trueConf, 0.01);
            Assert.assertEquals(0.3, falseConf, 0.01);

            softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.40);
            softEvidence.put("False", 0.60);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            trueConf = ShanksAgentBayesianReasoningCapability.getHypothesis(bn,
                    targetNodeName, "True");
            falseConf = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    bn, targetNodeName, "False");

            Assert.assertEquals(0.4, trueConf, 0.01);
            Assert.assertEquals(0.6, falseConf, 0.01);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidence3Times() {
        try {
            String targetNodeName = "History";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.70);
            softEvidence.put("False", 0.30);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            double trueConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "True");
            double falseConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "False");

            Assert.assertEquals(0.7, trueConf, 0.01);
            Assert.assertEquals(0.3, falseConf, 0.01);

            softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.40);
            softEvidence.put("False", 0.60);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            trueConf = ShanksAgentBayesianReasoningCapability.getHypothesis(bn,
                    targetNodeName, "True");
            falseConf = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    bn, targetNodeName, "False");

            Assert.assertEquals(0.4, trueConf, 0.01);
            Assert.assertEquals(0.6, falseConf, 0.01);

            softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.10);
            softEvidence.put("False", 0.90);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            trueConf = ShanksAgentBayesianReasoningCapability.getHypothesis(bn,
                    targetNodeName, "True");
            falseConf = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    bn, targetNodeName, "False");

            Assert.assertEquals(0.1, trueConf, 0.01);
            Assert.assertEquals(0.9, falseConf, 0.01);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidenceTwoTimes() {
        try {
            String targetNodeName = "History";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.70);
            softEvidence.put("False", 0.30);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            double trueConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "True");
            double falseConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName, "False");

            Assert.assertEquals(0.7, trueConf, 0.01);
            Assert.assertEquals(0.3, falseConf, 0.01);

            // 2nd time to check the "clear evidence" method
            softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.5);
            softEvidence.put("False", 0.5);

            ShanksAgentBayesianReasoningCapability.addSoftEvidence(bn,
                    targetNodeName, softEvidence);

            trueConf = ShanksAgentBayesianReasoningCapability.getHypothesis(bn,
                    targetNodeName, "True");
            falseConf = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    bn, targetNodeName, "False");

            Assert.assertEquals(0.5, trueConf, 0.01);
            Assert.assertEquals(0.5, falseConf, 0.01);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidenceInTwoNodes() {
        try {
            HashMap<String, HashMap<String, Double>> softEvidences = new HashMap<String, HashMap<String, Double>>();

            String targetNodeName1 = "History";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("True", 0.70);
            softEvidence.put("False", 0.30);
            softEvidences.put(targetNodeName1, softEvidence);

            String targetNodeName2 = "StrokeVolume";
            softEvidence = new HashMap<String, Double>();
            softEvidence.put("Low", 0.60);
            softEvidence.put("Normal", 0.30);
            softEvidence.put("High", 0.10);
            softEvidences.put(targetNodeName2, softEvidence);

            ShanksAgentBayesianReasoningCapability.addSoftEvidences(bn,
                    softEvidences);

            double trueConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "True");
            double falseConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "False");
            double lowConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName2, "Low");
            double normalConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName2, "Normal");
            double highConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName2, "High");

            Assert.assertEquals(0.7, trueConf, 0.05);
            Assert.assertEquals(0.3, falseConf, 0.05);
            Assert.assertEquals(0.6, lowConf, 0.05);
            Assert.assertEquals(0.3, normalConf, 0.05);
            Assert.assertEquals(0.1, highConf, 0.05);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddSoftEvidenceInComplexNode() {
        try {
            HashMap<String, HashMap<String, Double>> softEvidences = new HashMap<String, HashMap<String, Double>>();

            String targetNodeName1 = "VentAlv";
            HashMap<String, Double> softEvidence = new HashMap<String, Double>();
            softEvidence.put("Zero", 0.15);
            softEvidence.put("Low", 0.1);
            softEvidence.put("Normal", 0.21);
            softEvidence.put("High", 0.55);
            softEvidences.put(targetNodeName1, softEvidence);

            ShanksAgentBayesianReasoningCapability.addSoftEvidences(bn,
                    softEvidences);

            double zeroConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "Zero");
            double lowConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "Low");
            double normalConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "Normal");
            double highConf = ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, targetNodeName1, "High");

            Assert.assertEquals(0.15, zeroConf, 0.01);
            Assert.assertEquals(0.10, lowConf, 0.01);
            Assert.assertEquals(0.21, normalConf, 0.01);
            Assert.assertEquals(0.550, highConf, 0.01);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void AddBeliefInferAndQueryAndResetOneEvidenceBayesCapabilityTest() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);
            nodeName = "KinkedTube";
            status = "True";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);

            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.06F, value, 0.01F);

            ShanksAgentBayesianReasoningCapability.clearEvidence(this.bn,
                    "MinVol");
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.03F, value, 0.01F);
            queryNodeName = "MinVol";
            queryStatus = "High";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.02F, value, 0.01F);
            queryNodeName = "TPR";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * 
     */
    @Test
    public void ModifyEvidenceInAGivenEvidenceNode() {
        try {
            String nodeName = "MinVol";
            String status = "High";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);

            String queryNodeName = "TPR";
            String queryStatus = "Normal";
            float value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.23, value, 0.01F);

            status = "Normal";
            ShanksAgentBayesianReasoningCapability.addEvidence(this.bn,
                    nodeName, status);

            queryNodeName = "TPR";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.3961F, value, 0.001F);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.85, value, 0.01F);

            ShanksAgentBayesianReasoningCapability.clearEvidence(bn, nodeName);
            queryNodeName = "ArtCO2";
            queryStatus = "Normal";
            value = ShanksAgentBayesianReasoningCapability.getHypothesis(
                    this.bn, queryNodeName, queryStatus);
            Assert.assertEquals(0.68F, value, 0.01F);

        } catch (UnknowkNodeStateException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (UnknownNodeException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
