package sma.commerce.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;

public class AcheteurAgent_old extends Agent {

	@Override
	protected void setup() {
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage aclMessage = receive();
				if(aclMessage !=null){
					String book = aclMessage.getContent();
					System.out.println("book name is :"+book);
					/*
					 * negociation
					 */
					
					ACLMessage aclMessage2 = aclMessage.createReply();
					/**
					 * OR
					 * 					
					 * ACLMessage aclMessage2 = new ACLMessage(ACLMessage.INFORM);
					   aclMessage2.addReceiver(aclMessage.getSender());
					 */
					aclMessage2.setPerformative(ACLMessage.INFORM);
					aclMessage2.setContent("demande recue , Traitement en cours");
					send(aclMessage2);
				}
				else block();
			}
		});
	}
}
