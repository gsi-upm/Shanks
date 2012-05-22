package es.upm.dit.gsi.shanks.hackerhan.attack;

import jason.asSemantics.Message;

import java.util.HashMap;
import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.hackerhan.model.Values;
import es.upm.dit.gsi.shanks.model.element.exception.UnsupportedNetworkElementFieldException;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.RouterDNS;

/**
 * Class to represent the bot to be deployed in a Hacked HAN
 * 
 * @author Alberto Mardomingo
 */
public class Bot extends SimpleShanksAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1410036330601812725L;
	
	private boolean attacking;
	private String target;
	private Scenario han;

	public Bot(String id, Scenario han) {
		super(id);
		attacking = false;
		this.han = han;
	}

	@Override
	public void checkMail() {
		List<Message> messages = this.getInbox();
		// Message syntax: "StartAttack:Target"
		for(Message message: messages){
			if(((String)message.getPropCont()).contains(Values.ATTACK_ORDER)){
				// Jaffa, kree!!
				this.target = ((String)message.getPropCont()).split(":")[1];
				this.attacking = true;
			} else if (((String)message.getPropCont()).contains(Values.STOP_ORDER)){
				this.attacking = false;
			}
		}
		// TODO: if "StartAttack" --> launch the attack
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		if(attacking){
			// Jaffa, Tal shak! 
//			Gateway gateway = (Gateway)simulation.getScenario().getNetworkElement(target);
			// TODO: increase load count.
			RouterDNS routerISP = (RouterDNS)((RouterDNS) simulation.getScenario().getNetworkElement(Values.ISP_GATEWAY_ID))
					.getGateway(Values.ENTERPRISE_GATEWAY_ID, this);
			RouterDNS router = (RouterDNS) simulation.getScenario().getNetworkElement(Values.ENTERPRISE_GATEWAY_ID);
			int cong = (Integer)router.getProperty(RouterDNS.PROPERTY_CONGESTION);
			try {
				router.updatePropertyTo(RouterDNS.PROPERTY_CONGESTION, cong + Values.LOAD_INCREASE);
			} catch (UnsupportedNetworkElementFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Scenario getHAN(){
		return this.han;
	}

}
