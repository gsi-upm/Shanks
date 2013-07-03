/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknowkNodeStateException;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknownNodeException;

/**
 * @author a.carrera
 * 
 */
public class ShanksAgentBayesianReasoningCapabilityTest {

    ProbabilisticNetwork bn;
    BayesianReasonerShanksAgent agent;

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        LogManager lm = LogManager.getLogManager();
        File configFile = new File("src/test/resources/logging.properties");
        lm.readConfiguration(new FileInputStream(configFile));
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
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

}
