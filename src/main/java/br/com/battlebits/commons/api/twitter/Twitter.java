package br.com.battlebits.commons.api.twitter;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter {

	public static boolean tweet(TwitterAccount account, String tweet) throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(account.getConsumerKey())
				.setOAuthConsumerSecret(account.getConsumerSecret())
                .setOAuthAccessToken(account.getAccessToken())
				.setOAuthAccessTokenSecret(account.getAccessSecret());
		twitter4j.Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Status status = twitter.updateStatus(tweet);
		return status != null;
	}

}
