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
package es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.unbbayes;

import unbbayes.prs.bn.ProbabilisticNetwork;

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
