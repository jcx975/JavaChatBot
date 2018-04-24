package chatbot;

import chatbot.components.BotMath;
import chatbot.components.BotDirections;
import chatbot.components.WeatherBot;
import java.util.Scanner;

import javax.swing.JOptionPane;

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

public class JavaChatBot
{
	public static void main( String[] args )
	{	
		JavaChatBotGUI gui = new JavaChatBotGUI();
		gui.addText( "Please wait, library is loading..." );
		gui.allowInput = false;
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );
		StanfordCoreNLP pipeline = new StanfordCoreNLP( props );
		gui.allowInput = true;
		gui.addText( "Loading complete" );
	}
}
