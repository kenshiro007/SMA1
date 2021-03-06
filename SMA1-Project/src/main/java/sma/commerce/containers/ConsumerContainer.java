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

public class ConsumerContainer extends Application {

	private ConsumerAgent consumerAgent;
	public static void main(String[] args) {
		launch(ConsumerContainer.class);
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
			AgentController agentController = consumerContainer.createNewAgent("consumerAgent", ConsumerAgent.class.getName(), new Object[]{this});
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
		primaryStage.setTitle("Consommateur");
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(10));
		Label label_book = new Label("book ");
		final TextField textField_book = new TextField();
		Button button_by = new Button("Send");
		hBox.getChildren().add(label_book);
		hBox.getChildren().add(textField_book);
		hBox.getChildren().add(button_by);
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
		
		button_by.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				String book = textField_book.getText();
				GuiEvent guiEvent = new GuiEvent(this, 1);
				guiEvent.addParameter(book);
				consumerAgent.onGuiEvent(guiEvent);
				//observableList.add(book);
				
			}
		});
		primaryStage.show();
		
		
	}

	public ConsumerAgent getConsumerAgent() {
		return consumerAgent;
	}

	public void setConsumerAgent(ConsumerAgent consumerAgent) {
		this.consumerAgent = consumerAgent;
	}
	
	public void viewMessage(GuiEvent ev){
		String msg = ev.getParameter(0).toString();
		observableList.add(msg);
	}
	
}
