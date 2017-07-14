package sma.commerce.containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import sma.commerce.agents.ConsumerAgent;

public class ConsumerContainer {

	public static void main(String[] args) {
		
		try{
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "10.188.165.76");
			AgentContainer consumerContainer = runtime.createAgentContainer(profileImpl);
			System.out.println("cons agent name "+ConsumerAgent.class.getName());
			AgentController agentController = consumerContainer.createNewAgent("consumerAgent", ConsumerAgent.class.getName(), new Object[]{});
			agentController.activate();
			agentController.start();
			consumerContainer.start();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
