package org.tonyzt.kata.conversationStream;

import org.tonyzt.kata.OutStream;
import test.org.tonyxzt.kata.spike.PlayerFinder;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 03/07/11
 * Time: 8.47
 * To change this template use File | Settings | File Templates.
 */
public class OutStreamImpl implements OutStream {
    Random rand;
    Twitter twitter = new TwitterFactory().getInstance();
    String player;

    public OutStreamImpl(Twitter twitter,String player) {
        this.twitter = twitter;
        this.player = player;
        rand = new Random(System.currentTimeMillis());
    }

    @Override
    public void output(String out) {
        try {
            twitter.updateStatus("@"+player+" "+out + "   " +(new Integer((rand.nextInt()%10000))).toString());
        } catch (TwitterException eh) {
            throw new RuntimeException(eh.toString());
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
