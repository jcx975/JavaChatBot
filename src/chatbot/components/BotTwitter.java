package components;

import utils.InputData;

import java.util.*;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

public class BotTwitter {
	private static final String TWITTER_CONSUMER_KEY = "ADuDE9dtZ4nb5mOLg92XDWetu";
	private static final String TWITTER_SECRET_KEY = "VDAeQQbiySqS9mUtXjZ0mpd0Z4YmZhbQnL53s7LDx2Sd5e97Te";
	private static final String TWITTER_ACCESS_TOKEN = "15487521-vKrTEPvGd7xkn3IZ4g79zZLL7xWsoDpYIkupOJ4hR";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "ni8Axkav3nBqXXciKuSGUJFEDVapkZ7IKq1ORZjvaxjck";
	static Twitter twitter;

	public static void buildTwitterConfig() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY).setOAuthConsumerSecret(TWITTER_SECRET_KEY)
				.setOAuthAccessToken(TWITTER_ACCESS_TOKEN).setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public static String retrieveTweet(InputData input) throws TwitterException {
		buildTwitterConfig();
		String response = "";
		System.out.println(input.getTwitterHandle());
		List<Status> tweets = twitter.getUserTimeline(input.getTwitterHandle());
        for (Status tweet : tweets) {
        	response = "@" + tweet.getUser().getScreenName() + " - " + tweet.getText();
        	break;
        }		

		return response;
	}
}
