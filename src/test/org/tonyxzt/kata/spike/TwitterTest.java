package test.org.tonyxzt.kata.spike;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.OutStream;
import org.tonyzt.kata.twitter.TicketService;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 26/06/11
 * Time: 22.11
 * To change this template use File | Settings | File Templates.
 */
public class TwitterTest {

    OutStream writer;
    InStream inputData;


    TicketService ticketService;

    @Before
    public void init() {
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        inputData = mock(InStream.class);
    }

    @Test
    public void removeNotExistingTicketDoesNothing() throws Exception {
        TicketService ticketService = TicketService.getInstance();
        ticketService.initialize();
        ticketService.removeTicket("user1");
    }

    @Test
    public void selectUserToBeServedViaFifo()
    {
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        ticketService.assignTicketToUser("user1");
        ticketService.assignTicketToUser("user2");
        assertEquals("user1", ticketService.nextUserInWaitList());
        assertEquals("user2",ticketService.nextUserInWaitList());
    }




    @Test
    public void noTicketTwoSameUser() throws Exception {
        // arrange
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        ticketService.assignTicketToUser("user1");
        ticketService.assignTicketToUser("user1");
        ticketService.nextUserInWaitList();

        assertEquals("",ticketService.nextUserInWaitList());

    }

    @Test
    public void removeUsersFromTicketList() throws Exception {
        // arrange
        TicketService ticketService = TicketService.getInstance();
        ticketService.initialize();
        ticketService.assignTicketToUser("user1");
        ticketService.removeTicket("user1");
        assertEquals("",ticketService.nextUserInWaitList());
        ticketService.assignTicketToUser("user1");
        assertEquals("user1",ticketService.nextUserInWaitList());
        assertEquals("",ticketService.nextUserInWaitList());
    }

    @Test
    public void twoTickets() throws Exception {
        // arrange
        ticketService = TicketService.getInstance();
        ticketService.initialize();
        ticketService.assignTicketToUser("user1");
        ticketService.assignTicketToUser("user2");
        assertEquals("user1",ticketService.nextUserInWaitList());
        assertEquals("user2",ticketService.nextUserInWaitList());
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
    public void emptyTicketListReturnEmpty()
    {
        assertEquals("", ticketService.nextUserInWaitList());
    }

    private void stepNTimes(AnimalQuiz animalQuiz, int n) {
        for (int i=0;i<n;i++) {
            animalQuiz.step();
        }
    }
}
