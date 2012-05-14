package es.upm.dit.gsi.shanks.datacenter.agent;

import jason.functions.e;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.datacenter.model.Values;

public class SysAdmin extends SimpleShanksAgent implements BayesianReasonerShanksAgent {

	String bayesianNetworkFilePath;
	ProbabilisticNetwork bayesianNetwork;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4001442333333480506L;

	public SysAdmin(String id) {
		super(id);
		this.bayesianNetworkFilePath = Values.SYSADMIN_BAYESIAN_NETWORK_PATH;
		try {
			this.bayesianNetwork = ShanksAgentBayesianReasoningCapability.loadNetwork(this.bayesianNetworkFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void checkMail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProbabilisticNetwork getBayesianNetwork() {
		return this.bayesianNetwork;
	}

	@Override
	public void setBayesianNetwork(ProbabilisticNetwork bn) {
		this.bayesianNetwork = bn;
		//this.bayesianNetworkFilePath
	}

	@Override
	public String getBayesianNetworkFilePath() {
		return this.bayesianNetworkFilePath;
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		String to_repair = "0";
		String webServerLoad = "0";
		String routerLoad = "0";
		String log_status = "OK";
		
		try {
			ShanksAgentBayesianReasoningCapability.addEvidence(this, Values.SYSADMIN_REPAIR_NODENAME, to_repair);
			ShanksAgentBayesianReasoningCapability.addEvidence(this, Values.SYSADMIN_SERVER_LOAD_NODENAME, webServerLoad);
			ShanksAgentBayesianReasoningCapability.addEvidence(this, Values.SYSADMIN_ROUTER_LOAD_NODENAME, routerLoad);
			ShanksAgentBayesianReasoningCapability.addEvidence(this, Values.SYSADMIN_LOG_NODENAME, log_status);
			
			this.bayesianNetwork.updateEvidences();
			
			HashMap<String, Float> hypotheses = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(this, Values.SYSADMIN_ACTION_NODENAME);
			Iterator<String> actions = hypotheses.keySet().iterator();
			int randomResult = simulation.random.nextInt(100);
			
			int probability = 0;
			String action;
			
			while(probability < randomResult) {
				action = actions.next();
				probability += hypotheses.get(action)*100;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
