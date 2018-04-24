
package chatbot;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JavaChatBotGUI extends JFrame implements KeyListener
{
	JPanel chat = new JPanel();
	
	private TextField chatInput;
	private static TextArea chatDisplay;
	public static boolean allowInput = true;
	
	public JavaChatBotGUI()
	{
		super("Chat Window");
		setLayout(new FlowLayout());
		
		add(new Label("Chat: "));
		chatInput = new TextField(10);
		add(chatInput);
		chatDisplay = new TextArea(40,40);
		chatDisplay.setEditable(false);
		add(chatDisplay);
		
		// Enter action - used to enter text to chat
		// Used when user presses enter, or presses button
		Action enter = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addText(chatInput.getText());
				chatInput.setText("");
			}
		};

		chatInput.addActionListener(enter);
				
		setSize(400,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.add(chat);
		setVisible(true);
	}
	
	public void addText( String text )
	{
		if ( allowInput )
			chatDisplay.append( text + "\n" );
	}
	
	@Override public void keyTyped(KeyEvent evt) {}
	@Override public void keyPressed(KeyEvent evt) {}
	@Override public void keyReleased(KeyEvent evt) {}
}