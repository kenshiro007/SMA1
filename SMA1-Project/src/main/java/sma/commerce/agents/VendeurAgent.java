package sma.commerce.agents;


import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sma.commerce.containers.VendeurContainer;

public class VendeurAgent extends GuiAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VendeurContainer gui;
	@Override
	protected void setup() {
		gui = (VendeurContainer) getArguments()[0];
		gui.setVendeurAgent(this);
		
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			/**
			 * publication du service dans l annuaire
			 */
			@Override
			public void action() {
				DFAgentDescription df = new DFAgentDescription();
				df.setName(getAID());
				ServiceDescription sd = new ServiceDescription();
				sd.setType("vente");
				sd.setName("vente-livre");
				df.addServices(sd);
				try {
					DFService.register(myAgent, df);
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
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
				else{
					block();
				}
				
			}
		});
	}
	@Override
	public void onGuiEvent(GuiEvent ev) {		
	}
}
