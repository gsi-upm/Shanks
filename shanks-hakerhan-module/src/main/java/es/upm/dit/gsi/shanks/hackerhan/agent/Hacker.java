package es.upm.dit.gsi.shanks.hackerhan.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import unbbayes.prs.bn.ProbabilisticNetwork;
import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.BayesianReasonerShanksAgent;
import es.upm.dit.gsi.shanks.agent.capability.reasoning.bayes.ShanksAgentBayesianReasoningCapability;
import es.upm.dit.gsi.shanks.exception.UnkownAgentException;
import es.upm.dit.gsi.shanks.hackerhan.attack.Attack;
import es.upm.dit.gsi.shanks.hackerhan.attack.DDoS;
import es.upm.dit.gsi.shanks.hackerhan.attack.RootShell;
import es.upm.dit.gsi.shanks.hackerhan.attack.SQLInjection;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.exception.ScenarioNotFoundException;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

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
	
	private static int numberOfDDoSAttacks = 0;
	private static int numberOfRootAttacks = 0;
	private static int numberOfSQLAttacks = 0;
	
	/**
	 * The bayesian network file path
	 */
	private String bayesianNetworkPath;
	
	/**
	 * The ability of the hacker
	 */
	private int ability;

	private static final long serialVersionUID = -8386091575218484770L;

	public Hacker(String id, String bnPath) {
		super(id);
		this.bayesianNetworkPath = bnPath;
		this.bayesianNetwork = new ProbabilisticNetwork("HackerBN");
		this.ability = -1;
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
		if (this.ability == -1)
			simulation.random.nextInt(Values.MAX_ABILITY);
		if (this.attack.isRunning()){
			this.attack.execute();
			if (((DDoS)this.attack).numberSteps() == 0) 
				((DDoS)this.attack).stop();
		} else {

		try {
			ShanksAgentBayesianReasoningCapability.addEvidence(this, "Conexion", "OK");
			RouterDNS router = (RouterDNS) ((ComplexScenario)simulation.getScenario()).getScenario(Values.HAN_SCENARIO_ID +
					this.getID().charAt(this.getID().length()-1)).getNetworkElement(Values.HAN_ROUTER_ID);
			ShanksAgentBayesianReasoningCapability.addEvidence(this, "Conexion",
					String.valueOf(router.getProperty(RouterDNS.PROPERTY_CONNECTION)));
			// TODO: Updates the connection data
			
			// If 0 --> lazy
			// Else --> lets hack!
			if (simulation.random.nextInt(1) == 0) {
				ShanksAgentBayesianReasoningCapability.addEvidence(this, "Estado_De_Animo", "Vaguear");
			} else {
				ShanksAgentBayesianReasoningCapability.addEvidence(this, "Estado_De_Animo", "Hackear");
			}

			this.bayesianNetwork.updateEvidences(); // Is this correct??

			HashMap<String, Float> accionStatus = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(this, "Accion");
			Iterator<String> actions = accionStatus.keySet().iterator();
			
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
			float probability = 0; 
			while (probability < result){
				action = actions.next();
				probability += accionStatus.get(action)*100;
			}
			
			// I tell the bayesian network what am I doing.
			ShanksAgentBayesianReasoningCapability.addEvidence(this, "Accion", action);
			this.bayesianNetwork.updateEvidences();
			
			if (!action.equals(Values.ACTION_NONE)) {

				// I need to repeat the same thing than previously, to find out
				// which attack I am launching
				HashMap<String, Float> types = ShanksAgentBayesianReasoningCapability.getNodeStatesHypotheses(this, "Tipo_Ataque");
				Iterator<String> attacks = types.keySet().iterator();
				
				int type = simulation.random.nextInt(100);
				
				/*
				 * Order:
				 * 
				 * DDos, RootShell, SQLInjection, None.
				 */
				float attackProbaility = 0;
				String attack = Values.ATTACK_NONE;
				while(attackProbaility < type){
					attack = attacks.next();
					attackProbaility += types.get(attack)*100;
				}
				if(!this.targetsID.isEmpty()){
					String targetID = this.targetsID.get(0);
					this.attack = createAttack(action, attack, targetID, simulation);
				}
				if (action.equals(Values.ACTION_GET_BOT) && this.attack instanceof RootShell) {
					((RootShell)this.attack).installBot(simulation);
				} else if (this.attack != null){
					this.attack.execute();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
	
	/**
	 * Takes a String with the attack type and returns the Attack object
	 * 
	 * @param attackType - String with the attack type
	 * @param targetID - The target
	 * @param sim - The simulation
	 * @return - The attack to be executed.
	 */
	private Attack createAttack(String action, String attackType, String targetID, ShanksSimulation sim){
		Attack result = null;
		try {
			if (attackType.equalsIgnoreCase(Values.ATTACK_DDOS)) {
				result = new DDoS(this, targetID, sim);
				numberOfDDoSAttacks++;
			} else if (attackType.equalsIgnoreCase(Values.ATTACK_ROOT_SHELL)) {
				result = new RootShell(this, sim,
						((ComplexScenario) sim.getScenario())
								.getScenario(Values.HAN_SCENARIO_ID
										+ this.getID().charAt(this.getID().length() - 1)));
				if (action.equals(Values.ACTION_PROXY_ATTACK)){
					try {
						((RootShell)result).setAccessID(sim.getAgent(this.bots.get(0)).getID());
					} catch (UnkownAgentException e) {
						e.printStackTrace();
					}
				}
				numberOfRootAttacks++;
			} else if (attackType.equalsIgnoreCase(Values.ATTACK_SQL_INJECTION)) {
				result = new SQLInjection(this, sim);
				if (action.equals(Values.ACTION_PROXY_ATTACK)){
					try {
						((SQLInjection)result).setAccessID(sim.getAgent(this.bots.get(0)).getID());
					} catch (UnkownAgentException e) {
						e.printStackTrace();
					}
				}
				numberOfSQLAttacks++;
			}
		} catch (ScenarioNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns the ability of the hacker
	 * 
	 * @return
	 */
	public int getAbility(){
		return this.ability;
	}

	public static int getNumberOfDDoSAttacks(){
		return numberOfDDoSAttacks;
	}
	
	public static int getNumberOfRootAttacks(){
		return numberOfRootAttacks;
	}
	
	public static int getNumberOfSQLAttacks(){
		return numberOfSQLAttacks;
	}
}
