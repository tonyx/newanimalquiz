package test.org.tonyxzt.kata.spike;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.tonyzt.kata.*;
import org.tonyzt.kata.twitter.TicketService;
import twitter4j.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 27/06/11
 * Time: 23.19
 * To change this template use File | Settings | File Templates.
 */
public class TwitterMockedAcceptance {
    OutStream writer;
    InStream inputData;

    @Before
    public void setUp() throws Exception  {
        user = twitter.verifyCredentials();
        inputData = mock(InStream.class);

        writer = new OutStream() {
            @Override
            public void output(String out) {
                try {
                    twitter.updateStatus(out);
                } catch (TwitterException eh)
                {
                    throw new RuntimeException(eh.toString());
                }
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void close() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    private TicketService ticketService;
    private final static Twitter twitter = new TwitterFactory().getInstance();
    User user;


    // spike
    @Test
    @Ignore
    public void sendALetsPlayMessage() throws Exception {

      //  TwitterAdapter twitterAdpater = new TwitterAdapter();
        Twitter twitterMock = mock(Twitter.class);


        twitter.updateStatus("@AnimalQuiz let's play");
        ResponseList<Status> responses = twitter.getMentions();
        Assert.assertEquals(1, sizeOfMessageListContainingAString(twitter, "let's play"));

    }



    private int sizeOfMessageListContainingAString(Twitter twitter, String content) throws Exception {
        ResponseList<Status> responses = twitter.getMentions();
        int toReturn=0;
        for (Status status : responses) {
            if (status.getText().contains(content)) {
                toReturn++;
            }
        }
        return toReturn;
    }



    @Test
    @Ignore
    public void shouldRecognizeMeWenIWantToPlayWithMySelfSendingMeALetsPlayMessage() throws Exception {
        //prepare
        Twitter twitter = mock(Twitter.class);
        twitter.updateStatus("@"+user.getScreenName()+" let's play");
        ResponseList<Status> responses = twitter.getMentions();
        String name= "";
        for (Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")) {
                name = theStatus.getUser().getScreenName();
                twitter.updateStatus("@"+name+" Ok, think about an animal");
            }
        }

        // assert
        Assert.assertEquals(1,sizeOfMessageListContainingAString(twitter,"Ok, think about an animal"));
    }
















    @Test
    public void addMeThatWantToPlayWithMeInTheThePlayersQueue() throws Exception {
        // arrange
        List<String> screenNameOfPlayers = new ArrayList<String>();
        twitter.updateStatus("@" + user.getScreenName() + " let's play man");

        // act
        ResponseList<Status> responses = twitter.getMentions();
        for (Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")) {
                screenNameOfPlayers.add(theStatus.getUser().getScreenName());
            }
        }

        // assert
        Assert.assertEquals(1,screenNameOfPlayers.size());
    }

    @Test
    public void doNotAddTwiceTheSamePlayerInThePlayerList() throws Exception {
        // arrange
        Set<String> screenNameOfPlayers = new HashSet<String>();

        Status status = twitter.updateStatus("@"+user.getScreenName()+" let's play");
        Thread.sleep(1000);
        status = twitter.updateStatus("@"+user.getScreenName()+" let's play amico");

        // act
        ResponseList<Status> responses = twitter.getMentions();
        for (Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")) {
                screenNameOfPlayers.add(theStatus.getUser().getScreenName());
            }
        }

        // assert
        Assert.assertEquals(1,screenNameOfPlayers.size());
    }

    //todo
    @Test
    public void animalThatHasAlreadyATicketWillNotBeAddedInCaseOfRequest() throws Exception {
        Set<String> screenNameOfPlayers = new HashSet<String>();
        Twitter twitter = new TwitterFactory().getInstance();
        User user = twitter.verifyCredentials();
        Status status = twitter.updateStatus("@"+user.getScreenName()+" let's play") ;
        Thread.sleep(1000);
        status = twitter.updateStatus("@"+user.getScreenName()+" let's play amico");
    }

    @Test
    public void associateATicketToTheUsers() throws Exception {
        // arrange
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        //Long ticket = ticketService.assignTicketToUser("user");
        //Assert.assertEquals(1L, (long)ticket);

    }

    @Test
    public void selectTopUserFromThePlayersList() throws Exception {
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        //Long ticket = ticketService.assignTicketToUser("first user");
        //Long ticket2 = ticketService.assignTicketToUser("second user");

        String user = ticketService.nextUserInWaitList();

        Assert.assertEquals("first user",user);
    }




    @Test
    @Ignore
    public void testThreeLevelLearningUsingTwitterAsChannel() {
        NodeI startNode = new NonLeafNode("Is it big?", new LeafNode("elephant"), new LeafNode("mouse"));
        NodeI expected = new NonLeafNode("Is it big?", new LeafNode("elephant"), new NonLeafNode("Does it have 1000 legs?", new LeafNode("worm"), new LeafNode("mouse")));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, startNode);

        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        animalQuiz.start();
        stepNTimes(animalQuiz, 6);
        InOrder inOrder = inOrder(writer);

//        inOrder.verify(writer).output("think of an animal");
//        inOrder.verify(writer).output("Is it big?");
//        inOrder.verify(writer).output("What animal was?");
//        inOrder.verify(writer).output("What question would you suggest to distinguish a mouse from a worm?");
//        inOrder.verify(writer).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");
        org.junit.Assert.assertEquals(expected, animalQuiz.getNode());

        //when(inputData.getInput()).thenReturn("No").thenReturn("Yes").thenReturn("No").thenReturn("microb").thenReturn("Is it microscopic?").thenReturn("Yes");

        //stepNTimes(animalQuiz, 5);

//        inOrder.verify(writer).output("Is it big?");
//        inOrder.verify(writer).output("Does it have 1000 legs?");
//        inOrder.verify(writer).output("Is it a worm?");
//        inOrder.verify(writer).output("What animal was?");
//        inOrder.verify(writer).output("What question would you suggest to distinguish a worm from a microb?");
//        verify(writer).output("What should be the answer to the question \"Is it microscopic?\" to indicate a microb compared to a worm?");
    }


    //@After
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




     private void stepNTimes(AnimalQuiz animalQuiz, int n) {
        try {
             Thread.sleep(2000);
        } catch (Exception e)
        {
            System.out.println(e);
        }

        for (int i=0;i<n;i++) {
            animalQuiz.step();
            try {
                Thread.sleep(2000);
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}






