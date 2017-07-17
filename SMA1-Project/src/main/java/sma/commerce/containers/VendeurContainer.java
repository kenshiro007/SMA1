package sma.commerce.containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import sma.commerce.agents.ConsumerAgent;
import sma.commerce.agents.VendeurAgent;

public class VendeurContainer extends Application {

	private VendeurAgent vendeurAgent;
	final ObservableList<String> observableList = FXCollections.observableArrayList();
	ListView<String> listView = new ListView<String>(observableList);
	AgentContainer consumerContainer;
	
	public static void main(String[] args) {
		launch(VendeurContainer.class);
	}

	public void startContainer (){
		try{
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "10.188.165.76");
			consumerContainer = runtime.createAgentContainer(profileImpl);
			System.out.println("cons agent name "+ConsumerAgent.class.getName());
			consumerContainer.start();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("Vendeur");
		Label label_vendeur = new Label("vendeur ");
		final TextField textField_vendeur = new TextField();
		Button button_vendeur = new Button("Validate");
		
		HBox hBox = new HBox();
		hBox.getChildren().add(label_vendeur);
		hBox.getChildren().add(textField_vendeur);
		hBox.getChildren().add(label_vendeur);
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
		
		button_vendeur.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				String vendeur_name = textField_vendeur.getText();
				
				try {
					AgentController agentController = consumerContainer.createNewAgent("VEND1", VendeurAgent.class.getName(), new Object[]{this});
					agentController.activate();
					agentController.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public VendeurAgent getVendeurAgent() {
		return vendeurAgent;
	}
	public void setVendeurAgent(VendeurAgent vendeurAgent) {
		this.vendeurAgent = vendeurAgent;
	}
	public void viewMessage(GuiEvent ev){
		String msg = ev.getParameter(0).toString();
		observableList.add(msg);
	}
	
}
