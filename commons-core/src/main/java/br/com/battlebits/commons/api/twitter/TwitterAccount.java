package br.com.battlebits.commons.api.twitter;

public enum TwitterAccount {

    BATTLEBITSMC, //
    BATTLEBANS;



    public String getAccessSecret() {
        return System.getenv("TWITTER_" + this.name() + "_ACCESS_SECRET");
    }

    public String getAccessToken() {
        return System.getenv("TWITTER_" + this.name() + "_ACCESS_TOKEN");
    }

    public String getConsumerKey() {
        return System.getenv("TWITTER_" + this.name() + "_CONSUMER_KEY");
    }

    public String getConsumerSecret() {
        return System.getenv("TWITTER_" + this.name() + "_CONSUMER_SECRET");
    }

}
