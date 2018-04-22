package chatbot;

import chatbot.components.BotMath;
import chatbot.components.BotDirections;
import java.util.Scanner;

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

public class TEst 
{
	public static void main(String[] args )
	{		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );
		StanfordCoreNLP pipeline = new StanfordCoreNLP( props );

		boolean playing = true;
		
		while ( playing )
		{
		
			System.out.print("Please type a sentence: " );
			Scanner scan = new Scanner( System.in );
			String text = scan.nextLine();
			if ( text.toLowerCase().equals( "stop" ) )
			{
				playing = false;
			}
			
			if ( !playing )
				break;
			
			Annotation document = new Annotation( text );
			
			CoreDocument cd = new CoreDocument( text );
			
			pipeline.annotate(document);
			pipeline.annotate(cd);
			
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			
			String sentenceText = cd.sentences().get(0).text();
			
			System.out.println( sentenceText );
			
			String sentenceCategory = "none";
			
			int[] nums = new int[ 2 ];
			
			int numCount = 0;
			String operator = "none";
			String location = "none";
			
			// Get the topic of the sentence
			for ( CoreMap sentence : sentences )
			{
				for ( CoreLabel token : sentence.get( TokensAnnotation.class ) )
				{
					String word = token.getString( TextAnnotation.class ).toLowerCase();
					
					if ( BotMath.mathOperations.contains( word ) )
					{
						sentenceCategory = "math";
						operator = word;
					} else if ( word.equals( "directions" ) )
					{
						sentenceCategory = "directions";
					}
				}
			}
			
			// Retrieve details from the sentence based on the perceived category.
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
						location = word;
				}
			}
			
			if ( sentenceCategory.equals( "math" ) )
			{
				System.out.println( "Interpretation: " + nums[ 0 ] + " " + operator + " " + nums[ 1 ] + "." );
				System.out.println( BotMath.calculate( nums[ 0 ], nums[ 1 ], operator ) );
			} else if ( sentenceCategory.equals( "directions" ) )
			{
				System.out.println( "Interpretation: Directions to " + location );
				BotDirections.goToMaps( location );
			}
				
			
		
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
					System.out.println(String.format("Print: word: [%s] pos: [%s] ne: [%s]", word, pos, ne));
				}
			}
		}
	}
}
