package sma.commerce.containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sma.commerce.agents.AcheteurAgent;
import sma.commerce.agents.ConsumerAgent;

public class AcheteurContainer extends Application {

	private AcheteurAgent acheteurAgent;
	public static void main(String[] args) {
		launch(AcheteurContainer.class);
	}

	final ObservableList<String> observableList = FXCollections.observableArrayList();
	ListView<String> listView = new ListView<String>(observableList);
	
	public void startContainer (){
		try{
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "10.188.165.76");
			AgentContainer consumerContainer = runtime.createAgentContainer(profileImpl);
			System.out.println("cons agent name "+ConsumerAgent.class.getName());
			AgentController agentController = consumerContainer.createNewAgent("ACHT1", AcheteurAgent.class.getName(), new Object[]{this});
			agentController.activate();
			agentController.start();
			consumerContainer.start();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("Acheteur");
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10));
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hBox);
		
		VBox vBox = new VBox();
		GridPane gridPane = new GridPane();
		gridPane.add(listView, 0, 0);
		
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().add(gridPane);
		borderPane.setCenter(vBox);
		
		Scene scene = new Scene(borderPane,400,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public AcheteurAgent getAcheteurAgent() {
		return acheteurAgent;
	}
	public void setAcheteurAgent(AcheteurAgent acheteurAgent) {
		this.acheteurAgent = acheteurAgent;
	}
	public void viewMessage(GuiEvent ev){
		String msg = ev.getParameter(0).toString();
		observableList.add(msg);
	}
	
}
