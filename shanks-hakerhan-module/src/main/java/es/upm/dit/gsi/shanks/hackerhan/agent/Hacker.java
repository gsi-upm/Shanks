package es.upm.dit.gsi.shanks.hackerhan.agent;

import java.util.ArrayList;
import java.util.HashMap;

import unbbayes.prs.bn.ProbabilisticNetwork;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.hackerhan.attack.Attack;
import es.upm.dit.gsi.shanks.hackerhan.attack.DDoS;
import es.upm.dit.gsi.shanks.hackerhan.attack.RootShell;
import es.upm.dit.gsi.shanks.hackerhan.attack.SQLInjection;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;

/**
 * Hacker agent is a malicious agent that investigate security failures and try
 * to exploit them. In this simulation the attacks will be centralized in a
 * single company network (not the World Wide Web).
 * 
 * A hacker can start with the necessary knowledge to perform a certain attack,
 * or no knowledge at all. In the course of the simulation a hacker can learn
 * new knowledge about attacks in general, that can lead to improve his own
 * skills making attacks or acquire new ones.
 * 
 * There is a small chance that the Hacker network suffer a failure, in that
 * case the hacker stops all attacks and malicious actions until the failure is
 * resolved. Also there is a chance that a hacker is traced back from his
 * victims, in that case the hacker is put down.
 * 
 * @author Danny
 * 
 */
public class Hacker extends SimpleShanksAgent implements BayesianReasonerShanksAgent {
	
	/**
	 * The gateway target
	 */
	private ArrayList<String> targetsID;

	/**
	 * The bot network available to the hacker
	 */
	private ArrayList<String> bots;
	
	/**
	 * The attack
	 */
	private Attack attack;
	
	/**
	 * The bayesian network
	 */
	private ProbabilisticNetwork bayesianNetwork;
	
	/**
	 * The bayesian network file path
	 */
	private String bayesianNetworkPath;

	private static final long serialVersionUID = -8386091575218484770L;

	public Hacker(String id, String bnPath) {
		super(id);
		this.bayesianNetworkPath = bnPath;
		this.bayesianNetwork = new ProbabilisticNetwork("HackerBN");
		try{
			this.bayesianNetwork = ShanksAgentBayesianReasoningCapability.loadNetwork(bnPath);
		} catch (Exception e) {
			System.out.println("Could not load Probabilistic Network");
			e.printStackTrace();
		}
		// TODO make that the agent start with or without knowledge
	}

	@Override
	public void checkMail() {
		// TODO design the interaction between hacker agents throw mail.
		// ¿Attacks throw mail too?
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {

		try {
			ShanksAgentBayesianReasoningCapability.addEvidence(this, "Conexion", "OK");
			// TODO: Updates the connection data

			this.bayesianNetwork.updateEvidences(); // Is this correct??

			HashMap<String, Float> accionStatus = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(this, "Accion");
			
			// Find out which attack I am supposed to do.
			int result = simulation.random.nextInt(100);

			String action = Values.ACTION_NONE;

			/*
			 * This could be a little confusing, so let me explain: I generate a
			 * random int between 0 an 100. I am supposed to have several
			 * results from the bayesian network, which are like:
			 * 
			 * ACTION_NONE = 0.1 
			 * ACTION_PROXY_ATTACK = 0.3 
			 * ACTION_DIRECT_ATTACK = 0.3 
			 * ACTION_GET_BOT = 0.3
			 * 
			 * So, now, I will rearrange this, by adding its values so I will
			 * get a range for each option. Graphically it will be something
			 * like:
			 * 
			 *            result 
			 *               |
			 *               V
			 *  0 ---- 10 ------------ 40 ------------ 70 ------------ 100
			 *    NONE        PROXY          DIRECT          GET_BOT
			 * 
			 * So I will proceed with a proxy attack
			 * 
			 * I am sure as hell there is a better way of doing this, but I have
			 * not been able to find it.
			 */
			if (0 <= result && result > accionStatus.get(Values.ACTION_NONE) * 100) {
				// No action
				action = Values.ACTION_NONE;
			} else if (accionStatus.get(Values.ACTION_NONE) * 100 <= result
					&& result < (accionStatus.get(Values.ACTION_NONE) + accionStatus.get(Values.ACTION_PROXY_ATTACK)) * 100) {
				// Proxied attack
				action = Values.ACTION_PROXY_ATTACK;
			} else if ((accionStatus.get(Values.ACTION_NONE) + accionStatus.get(Values.ACTION_PROXY_ATTACK)) * 100 <= result &&
					result < (accionStatus.get(Values.ACTION_NONE) + accionStatus.get(Values.ACTION_PROXY_ATTACK) + accionStatus.get(Values.ACTION_DIRECT_ATTACK)) * 100) {
				// Direct attack
				action = Values.ACTION_DIRECT_ATTACK;
			} else {
				// Get bot
				action = Values.ACTION_GET_BOT;
			}
			
			// I tell the bayesian network what am I doing.
			ShanksAgentBayesianReasoningCapability.addEvidence(this, "Accion", action);
			this.bayesianNetwork.updateEvidences();
			
			if (!action.equals(Values.ACTION_NONE)) {

				// I need to repeat the same thing than previously, to find out
				// which attack I am launching
				HashMap<String, Float> types = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(this, "Tipo_Ataque");
				
				int type = simulation.random.nextInt(100);
				
				/*
				 * Order:
				 * 
				 * DDos, RootShell, SQLInjection, None.
				 */
				
				if(0 < type && type <= types.get(Values.ATTACK_DDOS)*100){
					// DDoS
					this.attack = new DDoS(this, targetsID.get(0));
					this.attack.execute();
				} else if (types.get(Values.ATTACK_DDOS)*100 < type &&
						type <= (types.get(Values.ATTACK_DDOS) + types.get(Values.ATTACK_ROOT_SHELL))*100) {
					this.attack = new RootShell(this, simulation);
					this.attack.execute();
				} else if((types.get(Values.ATTACK_DDOS) + types.get(Values.ATTACK_ROOT_SHELL))*100 < type &&
						type <= (types.get(Values.ATTACK_DDOS) + types.get(Values.ATTACK_ROOT_SHELL) + types.get(Values.ATTACK_SQL_INJECTION))*100) {
					// SQL Injection
					this.attack = new SQLInjection(this, simulation);
					this.attack.execute();
				} else {
					// NONE
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Returns the list of bots avaliable for this hacker
	 * 
	 * @return ArrayList<String> with the ID's of the bots.
	 */
	public ArrayList<String> getBots() {
		return this.bots;
	}

	/**
	 * Returns the number of bots avaliable for this hacker
	 * 
	 * @return int - The number of bots.
	 */
	public int getBotCount() {
		return this.bots.size();
	}

	/**
	 * Adds a bot to the list.
	 * 
	 * @param botID
	 *            - The new Bot ID.
	 */
	public void addBot(String botID) {
		this.bots.add(botID);
	}

	@Override
	public ProbabilisticNetwork getBayesianNetwork() {
		return this.bayesianNetwork;
	}

	@Override
	public void setBayesianNetwork(ProbabilisticNetwork bn) {
		this.bayesianNetwork = bn;
	}

	@Override
	public String getBayesianNetworkFilePath() {
		return this.bayesianNetworkPath;
	}
	
}
