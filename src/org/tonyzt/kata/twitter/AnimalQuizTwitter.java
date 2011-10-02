package org.tonyzt.kata.twitter;

import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.Node;
import org.tonyzt.kata.OutStream;
import org.tonyzt.kata.conversationStream.InStreamImpl;
import org.tonyzt.kata.conversationStream.OutStreamImpl;
import twitter4j.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/06/11
 * Time: 19.32
 * To change this template use File | Settings | File Templates.
 */
public class AnimalQuizTwitter {
    public static void main(String[] string) {
        try {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.verifyCredentials();
        InStream twitterINStream = new InStreamImpl(twitter,"animalquiz2");
        OutStream twitterOutStream = new OutStreamImpl(twitter,"animalquiz2");
        AnimalQuiz twitterAnimalQuiz = new AnimalQuiz(twitterINStream,twitterOutStream,new Node("elephant"));
            new AnimalQuizTwitter().removeAllMessagesMentioningMeThatICanRemove(twitter);
        twitterAnimalQuiz.start();
        while(true) {
            twitterAnimalQuiz.step();
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllMessagesMentioningMeThatICanRemove(Twitter twitter) throws Exception {
       // Twitter twitter = new TwitterFactory().getInstance();
        ResponseList<Status> responses = twitter.getMentions();
        for (Status status : responses ) {
            try {
             twitter.destroyStatus(status.getId());
            } catch (TwitterException te)
            {
            }
        }
    }
}
