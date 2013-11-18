/**
 * es.upm.dit.gsi.shanks
 * 02/04/2012
 */
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.smile;

import smile.Network;

/**
 * An interface that a Shanks Agent mut implement to obtain bayessian reasoning.
 * 
 * @author a.carrera
 * 
 */
public interface BayesianReasonerShanksAgent {

    /**
     * @return the ProbabilisticNetwork object with the reasoning network
     */
    public Network getBayesianNetwork();

    /**
     * Set the Bayesian network of the agent
     * 
     * @param bn
     */
    public void setBayesianNetwork(Network bn);

    /**
     * @return the path to load the Bayesian Network path
     */
    public String getBayesianNetworkFilePath();
}
