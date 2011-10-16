package test.org.tonyxzt.kata.spike;

import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 02/07/11
 * Time: 13.50
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerFinder {
    Set<String> getWannaBePlayers() throws TwitterException;
    Set<String> getWannaBePlayers(Set<Status> statuses) throws TwitterException;
    Set<String> getPlayersBut(Set<String> players) throws TwitterException;
    Set<String> getPlayersBut(Set<Status> statusesToExclude, Set<String> players) throws TwitterException;

}
