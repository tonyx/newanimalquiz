package test.org.tonyxzt.kata.spike;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tonyzt.kata.*;
import org.tonyzt.kata.twitter.TicketService;
import twitter4j.*;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/06/11
 * Time: 20.23
 * To change this template use File | Settings | File Templates.
 */
public class TwitterAcceptanceTest {
    OutStream writer;
    InStream inputData;
    InStream realInputData;

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


        realInputData = new InStream() {
            @Override
            public String getInput() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

    }

    private TicketService ticketService;
    private final static  Twitter twitter = new TwitterFactory().getInstance();
    User user;

    // spike





    @Test
    public void findPeopleThatWantstoPlayWithNoDuplication() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");
        twitter.updateStatus("@AnimalQuiz let's play man");
        PlayerFinder playerFinder = new TwitterAnimalPlayer();
        Set<String> players = playerFinder.getWannaBePlayers();
        Assert.assertEquals(1,players.size());
    }


    @Test
    public void doNotConsiderTwiceTheRequestOfPlaying() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");
        Set<Status> statusesToExclude = new HashSet<Status>();
        PlayerFinder playerFinder = new TwitterAnimalPlayer();

        Set<String> players = playerFinder.getWannaBePlayers(statusesToExclude);
        Assert.assertEquals(1,players.size());

        Assert.assertEquals(1,statusesToExclude.size());
        players = playerFinder.getWannaBePlayers(statusesToExclude);
        Assert.assertEquals(0,players.size());
    }

    @Test
    public void doNotConsiderTwiceTheRequestOfPlayingUsingPlayerExcludeList() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");
        Set<Status> statusesToExclude = new HashSet<Status>();
        PlayerFinder playerFinder = new TwitterAnimalPlayer();

        Set<String> players = playerFinder.getWannaBePlayers(statusesToExclude);
        Assert.assertEquals(1,players.size());

        Assert.assertEquals(1,statusesToExclude.size());
        players = playerFinder.getPlayersBut(players);
        Assert.assertEquals(0,players.size());
    }

    @Test
    public void doNotConsiderTwiceTheRequestOfPlayingExclidingPlayersAndStatuses() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");
        Set<Status> statusesToExclude = new HashSet<Status>();
        PlayerFinder playerFinder = new TwitterAnimalPlayer();

        Set<String> players = playerFinder.getWannaBePlayers(statusesToExclude);
        Assert.assertEquals(1,players.size());

        Assert.assertEquals(1,statusesToExclude.size());
        players = playerFinder.getPlayersBut(statusesToExclude,players);
        Assert.assertEquals(0,players.size());
    }


    @Test
    public void sendTicket() throws Exception{
        twitter.updateStatus("@AnimalQuiz let's play");
        Set<Status> statusToExclude = new HashSet<Status>();
        PlayerFinder playerFinder = new TwitterAnimalPlayer();

        Set<String> players = playerFinder.getWannaBePlayers(statusToExclude);
        TicketService ticketService = TicketService.getInstance();
        ticketService.assignTicketToUser((String)players.toArray()[0]);
    }


    @Test
    public void playWithMySelf() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");
        Set<Status> statusToExclude = new HashSet<Status>();
        PlayerFinder playerFinder = new TwitterAnimalPlayer();

        Set<String> players = playerFinder.getWannaBePlayers(statusToExclude);
        TicketService ticketService = TicketService.getInstance();
        ticketService.assignTicketToUser((String)players.toArray()[0]);
    }


    @Test
    public void setNextPlayerInWaitList() throws Exception {
        twitter.updateStatus("@AnimalQuiz let's play");

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
//        Long ticket = ticketService.assignTicketToUser("user");
 //       Assert.assertEquals(1L, (long)ticket);

    }

    @Test
    public void selectTopUserFromThePlayersList() throws Exception {
        ticketService = TicketService.getInstance();
        ticketService.initialize();
 //       Long ticket = ticketService.assignTicketToUser("first user");
  //      Long ticket2 = ticketService.assignTicketToUser("second user");

        String user = ticketService.nextUserInWaitList();

        Assert.assertEquals("first user",user);
    }




    @Test
    public void testThreeLevelLearningUsingTwitterAsChannel() {
        NodeI startNode = new NonLeafNode("Is it big?", new LeafNode("elephant"), new LeafNode("mouse"));
        NodeI expected = new NonLeafNode("Is it big?", new LeafNode("elephant"), new NonLeafNode("Does it have 1000 legs?", new LeafNode("worm"), new LeafNode("mouse")));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, startNode);

        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        animalQuiz.start();
        stepNTimes(animalQuiz, 5);
        org.junit.Assert.assertEquals(expected, animalQuiz.getNode());

    }


    @Test
    public void getTheLastMessagesOfUser() throws Exception {
        Twitter twitter = new TwitterFactory().getInstance();
        User user = twitter.verifyCredentials();
        twitter.updateStatus("@AnimalQuiz first");

        TwitterMessageGetter messageGetter = new TwitterMessageGetter(twitter,"AnimalQuiz");
        List<String> messages = messageGetter.lastMessages();
        Assert.assertEquals(1,messages.size());
        messages = messageGetter.lastMessages(messages);
        Assert.assertEquals(0,messages.size());
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
