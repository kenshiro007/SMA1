package sma.commerce.agents;


import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import sma.commerce.containers.ConsumerContainer;

public class ConsumerAgent extends GuiAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConsumerContainer gui;
	@Override
	protected void setup() {
		gui = (ConsumerContainer) getArguments()[0];
		gui.setConsumerAgent(this);
		
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
		
			@Override
			public void action() {
				ACLMessage aclMessage = receive();
				if(aclMessage!=null){
					GuiEvent guiEvent = new GuiEvent(this, 1);
					guiEvent.addParameter(aclMessage.getContent());
					gui.viewMessage(guiEvent);
				}
				
			}
		});
	}
	@Override
	public void onGuiEvent(GuiEvent ev) {
		if(ev.getType()==1){
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			String book = ev.getParameter(0).toString();
			aclMessage.setContent(book);
			aclMessage.addReceiver(new AID("ACHT1",AID.ISLOCALNAME));
			send(aclMessage);
		}		
	}
}
