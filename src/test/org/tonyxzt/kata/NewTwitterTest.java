package test.org.tonyxzt.kata;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.org.tonyxzt.kata.spike.TwitterAnimalPlayer;
import twitter4j.*;

import javax.accessibility.AccessibleStateSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 03/07/11
 * Time: 10.32
 * To change this template use File | Settings | File Templates.
 */
public class NewTwitterTest {
    @Test
    public void getLastMessagesDirectedToAnimalQuizFromSomeUser() throws Exception
    {
        //prepare
        Twitter twitter = new TwitterFactory().getInstance();
        TwitterConversationWith twitterConversationWith = new TwitterConversationWith(twitter,"AnimalQuiz");
        twitter.updateStatus("@AnimalQuiz hi");
        Assert.assertEquals(1, twitterConversationWith.getLastMessages().size());
    }


    @Test
    public void getLastStatusMessages() throws Exception
    {
        //prepare
        Twitter twitter = new TwitterFactory().getInstance();
        TwitterConversationWith twitterConversationWith = new TwitterConversationWith(twitter,"AnimalQuiz");
        twitter.updateStatus("@AnimalQuiz hi");
        Assert.assertEquals(1, twitterConversationWith.getLastStatusMessages().size());
    }

    @Test
    public void getLastStatusMessagesWithNoRepetition() throws Exception {
        //prepare
        Twitter twitter = new TwitterFactory().getInstance();
        TwitterConversationWith twitterConversationWith = new TwitterConversationWith(twitter,"AnimalQuiz");
        twitter.updateStatus("@AnimalQuiz hi");
        List<Status> statuses = twitterConversationWith.getLastStatusMessages();
        Assert.assertEquals(1, twitterConversationWith.getLastStatusMessages().size());
        Assert.assertEquals(0, twitterConversationWith.getLastStatusMessages(statuses).size());
    }

    @Test
    public void getLastStatusMessagesWithNoRepetition2() throws Exception {
        //prepare
        Twitter twitter = new TwitterFactory().getInstance();
        TwitterConversationWith twitterConversationWith = new TwitterConversationWith(twitter,"AnimalQuiz");
        twitter.updateStatus("@AnimalQuiz hi");
        List<Status> statuses = twitterConversationWith.getLastStatusMessages();
        Assert.assertEquals(1, twitterConversationWith.getLastStatusMessages().size());
        twitter.updateStatus("@AnimalQuiz wow");
        statuses = twitterConversationWith.getLastStatusMessages(statuses);
        Assert.assertEquals(1, twitterConversationWith.getLastStatusMessages(statuses).size());
    }


    @Test
    public void getOnlyLastStatusMessages() throws Exception {
        //prepare
        Twitter twitter = new TwitterFactory().getInstance();
        TwitterConversationWith twitterConversationWith = new TwitterConversationWith(twitter,"AnimalQuiz");
        twitter.updateStatus("@AnimalQuiz first");
        twitter.updateStatus("@AnimalQuiz second");
        twitter.updateStatus("@AnimalQuiz third");
        List<Status> statuses = new ArrayList<Status>();
        String lastStatus = twitterConversationWith.getLastSingleStatusMessages(statuses);
        Assert.assertEquals("@AnimalQuiz third",lastStatus);
        Assert.assertEquals(3,statuses.size());

    }


    @After
    @Before
    public void removeAllMessagesMentioningMeThatICanRemove() throws Exception {
        Twitter twitter = new TwitterFactory().getInstance();
        User user = twitter.verifyCredentials();
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
