package sma.commerce.agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import sma.commerce.gui.ConsumerGui;

public class ConsumerAgent extends GuiAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConsumerGui gui;
	@Override
	protected void setup() {
		gui = new ConsumerGui();
		gui.setConsumerAgent(this);
		
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
	/*	parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {	
			@Override
			public void action() {
				gui.showMessage("One Shot Behaviour", true);
			}
		});
		
		parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,1000) {
			private int counter;
			@Override
			protected void onTick() {
				counter++;
				gui.showMessage("Ticker Behaviour "+counter, true);
			}
		});
		
		parallelBehaviour.addSubBehaviour(new Behaviour() {
			private int counter;
			@Override
			public boolean done() {
				if (counter == 8) return true;
				return false;
			}
			
			@Override
			public void action() {
				++counter;
				gui.showMessage("Generic Behaviour "+counter, true);
			}
		});
		
	*/	
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour(this) {
			private int counter;
			@Override
			public void action() {
				counter++;
				gui.showMessage("Cyclic Behaviour "+counter, true);
				ACLMessage aclMessage = receive();
				if(aclMessage !=null){
					gui.showMessage("Acte de communication: "+aclMessage.getPerformative(), true);
					gui.showMessage("Sender: "+aclMessage.getSender(), true);
					gui.showMessage("Content: "+aclMessage.getContent(), true);
				}
				else{
					block();
				}
			}
		});
		
	}
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		
		
	}
}
