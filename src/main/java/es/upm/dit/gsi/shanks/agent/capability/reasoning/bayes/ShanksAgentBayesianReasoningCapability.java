/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknowkNodeStateException;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.exception.UnknownNodeException;

/**
 * @author a.carrera
 * 
 */
public class ShanksAgentBayesianReasoningCapability {

    /**
     * Load a Bayesian network and return the probabilistic network object
     * 
     * @param networkPath
     * @return
     * @throws Exception 
     */
    public static ProbabilisticNetwork loadNetwork(String networkPath)
            throws Exception {
        return ShanksAgentBayesianReasoningCapability.loadNetwork(new File(
                networkPath));
    }

    /**
     * Load a Bayesian network and return the probabilistic network object
     * 
     * @param netFile
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("deprecation")
    public static ProbabilisticNetwork loadNetwork(File netFile)
            throws Exception {
        ProbabilisticNetwork net = null;
        BaseIO io = new NetIO();
        net = (ProbabilisticNetwork) io.load(netFile);
        net.compile();
        return net;
    }
    
    /**
     * Load the Bayesian network of the agent
     * 
     * @param agent
     * @throws Exception 
     */
    public static void loadNetwork(BayesianReasonerShanksAgent agent) throws Exception {
       ProbabilisticNetwork bn = ShanksAgentBayesianReasoningCapability.loadNetwork(agent.getBayesianNetworkFilePath());
       agent.setBayesianNetwork(bn);
    }

    /**
     * Add information to the Bayesian network to reason with it.
     * 
     * @param bn
     * @param nodeName
     * @param status
     * @throws Exception 
     */
    public static void addEvidence(ProbabilisticNetwork bn, String nodeName,
            String status) throws Exception {
        ProbabilisticNode node = ShanksAgentBayesianReasoningCapability
                .getNode(bn, nodeName);
        int states = node.getStatesSize();
        for (int i = 0; i < states; i++) {
            if (status.equals(node.getStateAt(i))) {
                node.addFinding(i);
                bn.updateEvidences();
                return;
            }
        }
        throw new UnknowkNodeStateException(bn, nodeName, status);
    }

    /**
     * Get the value of a status in a node
     * 
     * @param bn
     * @param nodeName
     * @param status
     * @return a float with the probability
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static float getHypothesis(ProbabilisticNetwork bn, String nodeName,
            String status) throws UnknownNodeException,
            UnknowkNodeStateException {
        ProbabilisticNode node = ShanksAgentBayesianReasoningCapability
                .getNode(bn, nodeName);
        int states = node.getStatesSize();
        for (int i = 0; i < states; i++) {
            if (status.equals(node.getStateAt(i))) {
                return node.getMarginalAt(i);
            }
        }
        throw new UnknowkNodeStateException(bn, nodeName, status);
    }

    /**
     * Return the complete node
     * 
     * @param bn
     * @param nodeName
     * @return the ProbabilisticNode object
     * @throws UnknownNodeException
     */
    public static ProbabilisticNode getNode(ProbabilisticNetwork bn,
            String nodeName) throws UnknownNodeException {
        ProbabilisticNode node = (ProbabilisticNode) bn.getNode(nodeName);
        if (node == null) {
            throw new UnknownNodeException(bn, nodeName);
        }
        return node;
    }

    /**
     * Add a set of evidences to the Bayesian network to reason with it.
     * 
     * @param bn
     * @param evidences
     *            hashmap in format <nodeName, status> to set evidences in the
     *            bayesian network
     * @throws Exception 
     */
    public static void addEvidences(ProbabilisticNetwork bn,
            HashMap<String, String> evidences)
            throws Exception {
        for (Entry<String, String> evidence : evidences.entrySet()) {
            ShanksAgentBayesianReasoningCapability.addEvidence(bn,
                    evidence.getKey(), evidence.getValue());
        }
    }

    /**
     * Query several states of a node
     * 
     * @param bn
     * @param nodeName
     * @param states
     * @return
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static HashMap<String, Float> getNodeHypotheses(
            ProbabilisticNetwork bn, String nodeName, List<String> states)
            throws UnknownNodeException, UnknowkNodeStateException {
        HashMap<String, Float> result = new HashMap<String, Float>();
        for (String status : states) {
            result.put(status, ShanksAgentBayesianReasoningCapability
                    .getHypothesis(bn, nodeName, status));
        }
        return result;
    }

    /**
     * Query several states of a set of nodes
     * 
     * @param bn
     * @param queries
     *            in format hashmap of <node, List of states>
     * @return results in format hashmap of <node, hashmap>. The second hashmap is <state, probability of the hypothesis>
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static HashMap<String, HashMap<String, Float>> getHypotheses(
            ProbabilisticNetwork bn, HashMap<String, List<String>> queries)
            throws UnknownNodeException, UnknowkNodeStateException {
        HashMap<String, HashMap<String, Float>> result = new HashMap<String, HashMap<String, Float>>();
        for (Entry<String, List<String>> query : queries.entrySet()) {
            HashMap<String, Float> partialResult = ShanksAgentBayesianReasoningCapability
                    .getNodeHypotheses(bn, query.getKey(), query.getValue());
            result.put(query.getKey(), partialResult);
        }
        return result;
    }

    /**
     * To know the full status of a node
     * 
     * @param bn
     * @param nodeName
     * @return hashmap in format <status, hypothesis>
     * @throws UnknownNodeException 
     */
    public static HashMap<String, Float> getNodeStatesHypotheses(ProbabilisticNetwork bn, String nodeName) throws UnknownNodeException {
        ProbabilisticNode node = ShanksAgentBayesianReasoningCapability.getNode(bn, nodeName);
        HashMap<String, Float> result = new HashMap<String, Float>();
        int statesNum = node.getStatesSize();
        for (int i=0; i<statesNum; i++) {
            String status = node.getStateAt(i);
            Float hypothesis = node.getMarginalAt(i);
            result.put(status, hypothesis);
        }
        return result;
    }

    /**
     * To know all values of all nodes of the Bayesian network
     * 
     * @param bn
     * @return hashmap in format <node, <status, hypothesis>>
     * @throws UnknownNodeException 
     */
    public static HashMap<String, HashMap<String, Float>> getAllHypotheses(ProbabilisticNetwork bn) throws UnknownNodeException {
        List<Node> nodes = bn.getNodes();
        HashMap<String, HashMap<String, Float>> result = new HashMap<String, HashMap<String,Float>>();
        for (Node node : nodes) {
            String nodeName = node.getName();
            HashMap<String, Float> hypotheses = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(bn, nodeName);
            result.put(nodeName, hypotheses);
        }
        return result;        
    }

    /**
     * Clear all evidences in the network 
     * 
     * @param bn
     * @throws Exception 
     */
    public static void clearEvidences(ProbabilisticNetwork bn) throws Exception {
        bn.initialize();
    }

    /**
     * Add information to the Bayesian network to reason with it.
     * 
     * @param agent
     * @param nodeName
     * @param status
     * @throws Exception 
     */
    public static void addEvidence(BayesianReasonerShanksAgent agent, String nodeName,
            String status) throws Exception {
        ShanksAgentBayesianReasoningCapability.addEvidence(agent.getBayesianNetwork(), nodeName, status);
    }

    /**
     * Get the value of a status in a node
     * 
     * @param agent
     * @param nodeName
     * @param status
     * @return a float with the probability
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static float getHypothesis(BayesianReasonerShanksAgent agent, String nodeName,
            String status) throws UnknownNodeException,
            UnknowkNodeStateException {
        return ShanksAgentBayesianReasoningCapability.getHypothesis(agent.getBayesianNetwork(), nodeName, status);
    }

    /**
     * Return the complete node
     * 
     * @param agent
     * @param nodeName
     * @return the ProbabilisticNode object
     * @throws UnknownNodeException
     */
    public static ProbabilisticNode getNode(BayesianReasonerShanksAgent agent,
            String nodeName) throws UnknownNodeException {
        return ShanksAgentBayesianReasoningCapability.getNode(agent.getBayesianNetwork(), nodeName);
    }

    /**
     * Add a set of evidences to the Bayesian network to reason with it.
     * 
     * @param agent
     * @param evidences
     *            hashmap in format <nodeName, status> to set evidences in the
     *            bayesian network
     * @throws Exception 
     */
    public static void addEvidences(BayesianReasonerShanksAgent agent,
            HashMap<String, String> evidences)
            throws Exception {
        ShanksAgentBayesianReasoningCapability.addEvidences(agent.getBayesianNetwork(), evidences);
    }

    /**
     * Query several states of a node
     * 
     * @param agent
     * @param nodeName
     * @param states
     * @return
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static HashMap<String, Float> getNodeHypotheses(
            BayesianReasonerShanksAgent agent, String nodeName, List<String> states)
            throws UnknownNodeException, UnknowkNodeStateException {
        return ShanksAgentBayesianReasoningCapability.getNodeHypotheses(agent.getBayesianNetwork(), nodeName, states);
    }

    /**
     * Query several states of a set of nodes
     * 
     * @param agent
     * @param queries
     *            in format hashmap of <node, List of states>
     * @return results in format hashmap of <node, hashmap>. The second hashmap is <state, probability of the hypothesis>
     * @throws UnknownNodeException
     * @throws UnknowkNodeStateException
     */
    public static HashMap<String, HashMap<String, Float>> getHypotheses(
            BayesianReasonerShanksAgent agent, HashMap<String, List<String>> queries)
            throws UnknownNodeException, UnknowkNodeStateException {
        return ShanksAgentBayesianReasoningCapability.getHypotheses(agent.getBayesianNetwork(), queries);
    }
    
    /**
     * 
     * To know the full status of a node
     * 
     * @param agent
     * @param nodeName
     * @return hashmap in format <status, hypothesis>
     * @throws UnknownNodeException 
     */
    public static HashMap<String, Float> getNodeStatesHypotheses(BayesianReasonerShanksAgent agent, String nodeName) throws UnknownNodeException {
        return ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(agent.getBayesianNetwork(), nodeName);
    }
    
    /**
     * To know all values of all nodes of the Bayesian network
     * 
     * @param agent
     * @return hashmap in format <node, <status, hypothesis>>
     * @throws UnknownNodeException 
     */
    
    public static HashMap<String, HashMap<String, Float>> getAllHypotheses(BayesianReasonerShanksAgent agent) throws UnknownNodeException {
        return ShanksAgentBayesianReasoningCapability.getAllHypotheses(agent.getBayesianNetwork());
    }
    
    /**
     * Clear all evidences in the Bayesian network of the agent
     * 
     * @param agent
     * @throws Exception 
     */
    public static void clearEvidences(BayesianReasonerShanksAgent agent) throws Exception {
        ShanksAgentBayesianReasoningCapability.clearEvidences(agent.getBayesianNetwork());
    }

}
