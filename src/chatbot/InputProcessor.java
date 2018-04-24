
package chatbot;

import chatbot.utils.InputData;

import chatbot.components.BotMath;
import chatbot.components.WeatherBot;
import chatbot.components.BotDirections;
import components.BotHelp;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
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
	
	public InputData processInput( String input )
	{
		String response = "";
		String interpretation = "";
		
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
				else if ( word.equals( "weather" ) )
					data.setCategory( "weather" );
				else if ( word.equals( "help" ) )
					data.setCategory( "help" );
			}
			System.out.println( sentence.get( TreeAnnotation.class ) );
		}
		
		for ( CoreMap sentence : sentences )
		{
			if ( data.isCategory( "math" ) )
			{
				List<CoreMap> numbers = sentence.get( CoreAnnotations.NumerizedTokensAnnotation.class );
				for ( CoreMap number : numbers )
				{
					if ( number.get( CoreAnnotations.NumericCompositeValueAnnotation.class ) != null )
						data.addInteger( number.get( CoreAnnotations.NumericCompositeValueAnnotation.class ).intValue() );
					//System.out.println(number.get( CoreAnnotations.NumericCompositeValueAnnotation.class )  );
				}
			} else if ( data.isCategory( "directions" ) || data.isCategory( "weather" ) )
			{
				for ( CoreLabel token : sentence.get( TokensAnnotation.class ) )
				{
					String partOfSpeech = token.get( PartOfSpeechAnnotation.class );
					String word = token.getString( TextAnnotation.class );
					
					if ( partOfSpeech.equals( "NNP" ) )
						data.setLocation( word );
				}
			}
		}
		
		if ( data.isCategory( "math" ) )
		{
			interpretation = data.getInteger( 0 ) + " " + data.getMathOperator() + " " + data.getInteger( 1 );
			response = Integer.toString( BotMath.calculate( data ) );
		}
		else if ( data.isCategory( "directions" ) )
		{
			interpretation = "Directions to " + data.getLocation();
			BotDirections.goToMaps( data );
			response = "Opening Google Maps.";
		} else if ( data.isCategory( "weather" ) )
		{
			interpretation = "Weather in " + data.getLocation();
			response = WeatherBot.getWeather( data );
		} else if ( data.isCategory("help") )
		{
			interpretation = "Help";
			response = BotHelp.getHelp();
		} else
			response = "Sorry, I didn't understand your input.";
		
		data.setInterpretation( interpretation );
		data.setResponse( response );
		return data;
	}
	
}

