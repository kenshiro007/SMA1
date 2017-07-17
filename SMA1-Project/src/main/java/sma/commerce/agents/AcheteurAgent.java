package sma.commerce.agents;


import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sma.commerce.containers.AcheteurContainer;
import sma.commerce.containers.ConsumerContainer;

public class AcheteurAgent extends GuiAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AcheteurContainer gui;
	@Override
	protected void setup() {
		gui = (AcheteurContainer) getArguments()[0];
		gui.setAcheteurAgent(this);
		
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
		
			@Override
			public void action() {
				MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage aclMessage = receive(messageTemplate);
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
	}
}
