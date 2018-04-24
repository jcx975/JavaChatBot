
package chatbot;

import chatbot.utils.InputData;

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

public class InputProcessor {
	Properties props;
	StanfordCoreNLP pipeline;
	
	public InputProcessor( Properties props, StanfordCoreNLP pipeline )
	{
		this.props = props;
		this.pipeline = pipeline;
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );
	}
	
	public String processInput( String input )
	{
		String response = "";
		
		Annotation document = new Annotation( input );
		CoreDocument cd = new CoreDocument( input );
		
		pipeline.annotate( document );
		pipeline.annotate( cd );
		
		List<CoreMap> sentences = document.get( SentencesAnnotation.class );
		String sentenceText = cd.sentences().get( 0 ).text();
		
		InputData data = new InputData( input );
		
		for ( CoreMap sentence : sentences )
		{
			for ( CoreLabel token : sentence.get( TokensAnnotation.class ) )
			{
				String word = token.getString( TextAnnotation.class ).toLowerCase();
				
				if ( BotMath.mathOperations.contains( word ) )
				{
					data.setCategory( "math" );
					data.setMathOperator( word );
				} else if ( word.equals( "directions" ) )
					data.setCategory( "directions" );
			}
		}
		
		for ( CoreMap sentence : sentences )
		{
			for ( CoreLabel token : sentence.get( TokensAnnotation.class ) )
			{
				String partOfSpeech = token.get( PartOfSpeechAnnotation.class );
				String word = token.getString( TextAnnotation.class );
				
				if ( data.isCategory( "math" ) && partOfSpeech.equals( "CD" ) )
					data.addInteger( Integer.parseInt( word ) );
				else if ( data.isCategory( "directions" ) && partOfSpeech.equals( "NNP" ) )
					data.setLocation( word );
			}
		}
		
		if ( data.isCategory( "math" ) )
			response = Integer.toString( BotMath.calculate( data ) );
		else if ( data.isCategory( "directions" ) )
		{
			BotDirections.goToMaps( data );
			response = "Opening Google Maps.";
		} else
			response = "Sorry, I didn't understand your input.";
		
			
		return response;
	}
	
}

