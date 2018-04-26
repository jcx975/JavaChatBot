


import java.util.List;
import java.util.Properties;

import components.BotDirections;
import components.BotHelp;
import components.BotMath;
import components.WeatherBot;
import components.BotTwitter;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import twitter4j.TwitterException;
import utils.InputData;

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
		
		boolean understood = true;
		
		pipeline.annotate( document );
		pipeline.annotate( cd );
		
		List<CoreMap> sentences = document.get( SentencesAnnotation.class );
		String sentenceText = cd.sentences().get( 0 ).text();

		int sentiment = 2;
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
				else if (word.equals( "tweet" ) || word.equals("tweets"))
					data.setCategory("tweet");
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
			} else if (data.isCategory("tweet"))
				{
					for (CoreLabel token : sentence.get( TokensAnnotation.class ) )
					{
						String word = token.getString( TextAnnotation.class );
						String firstChar = word.substring(0,1);
						System.out.println(firstChar);
						System.out.println(word.substring(1));
						//System.out.println(firstChar);
						//System.out.println(word.substring(1));

						if(firstChar.equals("@"))
						{
							data.setTwitterHandle(word.substring(1));
						}
					}
				}
			Tree tree = sentence.get( SentimentCoreAnnotations.SentimentAnnotatedTree.class );
			sentiment = RNNCoreAnnotations.getPredictedClass( tree );
			System.out.println( "Sentiment: " + sentiment );
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
		} else if (data.isCategory("tweet"))
		{
			interpretation = "Tweet from " + data.getTwitterHandle();
			try {
				response = BotTwitter.retrieveTweet( data );
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			understood = false;
		
		data.setSentiment( sentiment );
		
		if ( understood )
		{
			if ( sentiment < 2 )
				response = "Wow. Here's the answer...: " + response;
			else if ( sentiment == 2 )
				response = "Here is your answer: " + response;
			else if ( sentiment > 2 )
				response = "This is the answer you are looking for: " + response + "\nGlad to help!";
				
			data.setInterpretation( interpretation );
			data.setResponse( response );
		} else
		{
			data.setInterpretation( "none" );
			data.setResponse( "Sorry, I don't understand what you said." );
		}
				
		return data;
	}
	
}

