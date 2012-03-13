/**
 * 
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes;

import unbbayes.prs.bn.ProbabilisticNetwork;

/**
 * @author a.carrera
 *
 */
public interface BayesianReasonerShanksAgent {

    /**
     * @return the ProbabilisticNetwork object with the reasoning network
     */
    public ProbabilisticNetwork getBayesianNetwork();
    
    /**
     * Set the Bayesian network of the agent
     * 
     * @param bn
     */
    public void setBayesianNetwork(ProbabilisticNetwork bn);
    
    /**
     * @return the path to load the Bayesian Network path
     */
    public String getBayesianNetworkFilePath();    
}
