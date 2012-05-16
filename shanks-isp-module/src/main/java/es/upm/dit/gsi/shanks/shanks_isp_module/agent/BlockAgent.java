package es.upm.dit.gsi.shanks.shanks_isp_module.agent;

import jason.asSemantics.Message;

import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;

public class BlockAgent extends SimpleShanksAgent{

	public BlockAgent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void checkMail() {
		List<Message> messages = this.getInbox();
		// Message syntax: "StartAttack:Target"
		for(Message message: messages){
			if(((String)message.getPropCont()).contains(Values.COMPLAINT)){
				// Jaffa, kree!!
			} else if (((String)message.getPropCont()).contains(Values.UNBLOCK_PETITION)){
			}
		}
		
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		
	}

}
