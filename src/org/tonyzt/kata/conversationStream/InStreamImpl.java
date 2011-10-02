package org.tonyzt.kata.conversationStream;

import org.tonyzt.kata.InStream;
import sun.awt.windows.ThemeReader;
import test.org.tonyxzt.kata.TwitterConversationWith;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 03/07/11
 * Time: 8.56
 * To change this template use File | Settings | File Templates.
 */
public class InStreamImpl implements InStream {
    Twitter twitter;
    String player;
    List<Status> alreadyGotStatuses;
    TwitterConversationWith twitterConversation;

    public InStreamImpl(Twitter twitter, String player) {
        this.twitter=twitter;
        this.player = player;
        alreadyGotStatuses= new ArrayList<Status>();
        twitterConversation = new TwitterConversationWith(twitter,player);
    }

    @Override
    public String getInput() {
        try {
            return twitterConversation.getLastSingleStatusMessages(alreadyGotStatuses);
        } catch (TwitterException e)
        {
        }
        return "";
    }
}




