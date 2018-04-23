package chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import chatbot.components.BotMath;
import chatbot.components.BotDirections;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.*;
import java.util.*;
import java.util.List;


public class ChatGUI extends JFrame implements KeyListener{
	JPanel chat = new JPanel();
	
	private TextField chatInput;
	private static TextArea chatDisplay;
			
	
	ChatGUI()
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
				chatDisplay.append(chatInput.getText() + "\n");
				giveCommand(chatInput.getText());
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
	
	public static void main(String[] args) {
		new ChatGUI();
		
		chatDisplay.append("Hello! Please give me a command.\n");
	}
	
	public void giveCommand(String input) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );
		StanfordCoreNLP pipeline = new StanfordCoreNLP( props );

		Annotation document = new Annotation( input );
		
		CoreDocument cd = new CoreDocument( input );
		
		pipeline.annotate(document);
		pipeline.annotate(cd);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		String sentenceText = cd.sentences().get(0).text();
		
		String sentenceCategory = "none";
		
		int[] nums = new int[2];
		
		int numCount = 0;
		String operator = "none";
		
		//Get the topic of the sentence
		for(CoreMap sentence : sentences)
		{
			for(CoreLabel token : sentence.get(TokensAnnotation.class))
			{
				String word = token.getString(TextAnnotation.class).toLowerCase();
				
				if(BotMath.mathOperations.contains(word))
				{
					sentenceCategory = "math";
					operator = word;
				} else if (word.equals("directions"))
				{
					sentenceCategory = "directions";
				}
			}
		}
		
		for ( CoreMap sentence : sentences )
		{
			for ( CoreLabel token : sentence.get( TokensAnnotation.class ) )
			{
				String partOfSpeech = token.get( PartOfSpeechAnnotation.class );
				String word = token.getString( TextAnnotation.class );
				
				if ( sentenceCategory.equals( "math" ) && partOfSpeech.equals( "CD" ) )
				{
					if ( numCount <= 1 )
						nums[ numCount++ ] = Integer.parseInt( token.getString( TextAnnotation.class ) );
				} else if ( sentenceCategory.equals( "directions" ) && partOfSpeech.equals( "NNP" ) )
					BotDirections.goToMaps( word );
			}
		}
		
		if ( sentenceCategory.equals( "math" ) )
		{
			chatDisplay.append( "Interpretation: " + nums[ 0 ] + " " + operator + " " + nums[ 1 ] + ".\n" );
			chatDisplay.append( (BotMath.calculate( nums[ 0 ], nums[ 1 ], operator ) + "\n"));
		} else if ( sentenceCategory.equals( "directions" ) )
			
		
	
		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);
				
				//test
				chatDisplay.append(String.format("Print: word: [%s] pos: [%s] ne: [%s]", word, pos, ne) + "\n");
			}
		}

	}
	@Override public void keyTyped(KeyEvent evt) {}
	@Override public void keyPressed(KeyEvent evt) {}
	@Override public void keyReleased(KeyEvent evt) {}
}
