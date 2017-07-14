
package sma.commerce.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sma.commerce.agents.ConsumerAgent;

public class ConsumerGui extends JFrame {

	private ConsumerAgent consumerAgent;
	private JLabel jLabelAgent = new JLabel("Consumer");
	private JTextField jTextField = new JTextField(12);
	private JLabel jLabelBook = new JLabel("Book");
	private JTextField jTextFieldBook = new JTextField(12);
	private JButton jButtonSend = new JButton("Send");
	private JTextArea jTextAreaMessage = new JTextArea();
	
	public ConsumerGui() {
		JPanel jPanelN = new JPanel();
		jTextAreaMessage.setFont(new Font("Arial", Font.BOLD, 14));
		jTextAreaMessage.setEditable(false);
		jPanelN.setLayout(new FlowLayout());
		jPanelN.add(jLabelAgent);
		jPanelN.add(jTextField);
		jPanelN.add(jLabelBook);
		jPanelN.add(jTextFieldBook);
		jPanelN.add(jButtonSend);
		jPanelN.add(jTextAreaMessage);
		this.setLayout(new BorderLayout());
		this.add(jPanelN,BorderLayout.NORTH);
		this.add(new JScrollPane(jTextAreaMessage), BorderLayout.CENTER);
		this.setSize(600,400);
		this.setVisible(true);
		
	}
	public ConsumerAgent getConsumerAgent() {
		return consumerAgent;
	}
	public void setConsumerAgent(ConsumerAgent consumerAgent) {
		this.consumerAgent = consumerAgent;
	}
	public void showMessage(String msg, boolean append){
		if(append){
			jTextAreaMessage.append(msg+"\n");
		}
		else{
			jTextAreaMessage.setText(msg);
		}
	}
	
	
}
