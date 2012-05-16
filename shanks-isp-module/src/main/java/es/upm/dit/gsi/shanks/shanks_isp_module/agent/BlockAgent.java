package es.upm.dit.gsi.shanks.shanks_isp_module.agent;

import jason.asSemantics.Message;

import java.util.List;

import es.upm.dit.gsi.shanks.ShanksSimulation;
import es.upm.dit.gsi.shanks.agent.SimpleShanksAgent;
import es.upm.dit.gsi.shanks.hackerhan.model.scenario.HackerHANScenario;
import es.upm.dit.gsi.shanks.model.element.NetworkElement;
import es.upm.dit.gsi.shanks.model.element.device.Device;
import es.upm.dit.gsi.shanks.model.element.link.Link;
import es.upm.dit.gsi.shanks.model.scenario.ComplexScenario;
import es.upm.dit.gsi.shanks.model.scenario.Scenario;
import es.upm.dit.gsi.shanks.networkattacks.util.networkelements.EthernetLink;
import es.upm.dit.gsi.shanks.shanks_isp_module.model.Values;

public class BlockAgent extends SimpleShanksAgent{

	private String target;
	private String action;

	public BlockAgent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void checkMail() {
		List<Message> messages = this.getInbox();
		for(Message message: messages){
			if(((String)message.getPropCont()).contains(Values.COMPLAINT)){
				// Message syntax: "Complaint:Suspect"
				this.target = ((String)message.getPropCont()).split(":")[1];
				this.action = Values.ACTION_BLOCK;
				this.getInbox().remove(message);
				return;
			} else if (((String)message.getPropCont()).contains(Values.UNBLOCK_PETITION)){
				// Message syntax: "Petition:Client"
				this.target = ((String)message.getPropCont()).split(":")[1];
				this.action = Values.ACTION_UNBLOCK;
				this.getInbox().remove(message);
				return;
			} else {
				this.getInbox().remove(message);
			}
		}
		
	}

	@Override
	public void executeReasoningCycle(ShanksSimulation simulation) {
		
		ComplexScenario isp = ((ComplexScenario)simulation.getScenario());
		Device ispRouter = (Device)isp.getNetworkElement(Values.ISP_GATEWAY_ID);
		if(this.action.equals(Values.ACTION_BLOCK)){
			try {
				Scenario suspiciousNet = isp.getScenario(this.target);
				if(suspiciousNet.getCurrentStatus().equals(HackerHANScenario.STATUS_MONITORIZED)){
					NetworkElement maliciousRouter = suspiciousNet.getNetworkElement(Values.HAN_ROUTER_ID);
					for(Link suscriber: ispRouter.getLinks()){
						if(suscriber.getLinkedDevices().contains(maliciousRouter)){
							suscriber.updatePropertyTo(EthernetLink.STATUS_NOK, true);
							suscriber.updatePropertyTo(EthernetLink.STATUS_OK, false);
						}
					}
				} else {
					suspiciousNet.setCurrentStatus(HackerHANScenario.STATUS_MONITORIZED);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(this.action.equals(Values.ACTION_UNBLOCK)){
			if(Math.random()<0.5){
				try {
					Scenario netTorestore = isp.getScenario(this.target);
					netTorestore.setCurrentStatus(HackerHANScenario.STATUS_NORMAL);
					Device routerToRestore = (Device) netTorestore.getNetworkElement(Values.HAN_ROUTER_ID);
					for(Link suscriber: ispRouter.getLinks()){
						if(suscriber.getLinkedDevices().contains(routerToRestore)){
							suscriber.updatePropertyTo(EthernetLink.STATUS_NOK, false);
							suscriber.updatePropertyTo(EthernetLink.STATUS_OK, true);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private static final long serialVersionUID = 7915033908926580092L;
}
