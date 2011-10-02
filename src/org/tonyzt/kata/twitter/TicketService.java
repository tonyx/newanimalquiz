package org.tonyzt.kata.twitter;

import sun.security.krb5.internal.Ticket;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 26/06/11
 * Time: 11.44
 * To change this template use File | Settings | File Templates.
 */
public class TicketService {
    private static TicketService _instance;
    Long counter;
    Map<String,Long> tickets;
    Map<Long,String> mapTickets;
    private TicketService() {
        initialize();
    }

    public void assignTicketToUser(String user) {
        if (!mapTickets.values().contains(user)) {
            mapTickets.put(++counter,user);
            tickets.put(user,counter);
        }
    }

    public void initialize() {
        mapTickets  = new  HashMap<Long,String>();
        tickets = new HashMap<String, Long>();
        counter = 0L;
    }

    public static TicketService getInstance() {
        if (_instance==null) {
            _instance = new TicketService();
        }
        return _instance;
    }

    public void removeTicket(String screenName) {
        mapTickets.remove(tickets.get(screenName));
        tickets.remove(screenName);
    }

    public String nextUserInWaitList() {
        if (tickets.size()>0) {
            Collections.min(tickets.keySet());
            Long ticket = Collections.min(mapTickets.keySet());
            String toReturn = mapTickets.get(ticket);
            tickets.remove(mapTickets.get(ticket));
            mapTickets.remove(ticket);
            return toReturn;
        }
        return "";
    }
}
