package sma.commerce.agents;

import java.util.Map;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sma.commerce.gui.ConsumerGui;

public class ConsumerAgent_old extends GuiAgent {

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
				
			/**
			 * To receive all messages
			 * 
			 * 
			 */
				ACLMessage aclMessage = receive();
			
			/**
			 * to limit actions on specific message
			 * create a message template then push it into receive method
			 */
			/*	MessageTemplate messageTemplate = MessageTemplate.
						    and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), 
							    MessageTemplate.MatchOntology("achat"));
				
				ACLMessage aclMessage = receive(messageTemplate);
				*/
				if(aclMessage !=null){
					gui.showMessage("Acte de communication: "+aclMessage.getPerformative(aclMessage.getPerformative()), true);
					gui.showMessage("Sender: "+aclMessage.getSender(), true);
					gui.showMessage("Content: "+aclMessage.getContent(), true);
					gui.showMessage("X: "+aclMessage.getUserDefinedParameter("x"), true);
					gui.showMessage("Y: "+aclMessage.getUserDefinedParameter("y"), true);
				}
				else{
					block();
				}
			}
		});
		
	}
	@Override
	public void onGuiEvent(GuiEvent ev) {
		switch (ev.getType()) {
		case 1:
			/**
			 * get params from GUI object
			 */
			
			Map<String, Object> params = (Map<String, Object>) ev.getParameter(0);
			String agentAcheteur = (String) params.get("acheteur");
			String book = (String) params.get("book");
			/**
			 * create message
			 */
			ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
			aclMessage.addReceiver(new AID(agentAcheteur, AID.ISLOCALNAME));
			aclMessage.setOntology("achat-vente");
			aclMessage.setContent(book);
			/**
			 * send message
			 */
			send(aclMessage);
			break;

		default:
			break;
		}
		
	}
}
