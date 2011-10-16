package test.org.tonyxzt.kata.spike;

import test.org.tonyxzt.kata.spike.PlayerFinder;
import twitter4j.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 02/07/11
 * Time: 13.51
 * To change this template use File | Settings | File Templates.
 */
public class TwitterAnimalPlayer implements PlayerFinder {
    Twitter twitter;

    public TwitterAnimalPlayer(Twitter twitter) {
        this.twitter = twitter;
    }

    public TwitterAnimalPlayer() {
        this(new TwitterFactory().getInstance());
    }

    @Override
    public Set<String> getWannaBePlayers() throws TwitterException {
        Set toReturn = new HashSet<String>();
        ResponseList<Status> responses = twitter.getMentions();
        for (Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")) {
                toReturn.add(theStatus.getUser().getScreenName());
            }
        }
        return toReturn;
    }

    @Override
    public Set<String> getWannaBePlayers(Set<Status> statuses) throws TwitterException {
        Set toReturn = new HashSet<String>();
        ResponseList<Status> responses = twitter.getMentions();
        for (Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")&&!statuses.contains(theStatus)) {
                toReturn.add(theStatus.getUser().getScreenName());
                statuses.add(theStatus);
            }
        }
        return toReturn;
    }

    @Override
    public Set<String> getPlayersBut(Set<String> players) throws TwitterException {
        Set toReturn = new HashSet<String>();
        ResponseList<Status> responses = twitter.getMentions();
        for(Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")&&!players.contains(theStatus.getUser().getScreenName())) {
               toReturn.add(theStatus);
            }
        }
        return toReturn;
    }

    @Override
    public Set<String> getPlayersBut(Set<Status> statusesToExclude, Set<String> players) throws TwitterException {
        Set toReturn = new HashSet<String>();
        ResponseList<Status> responses = twitter.getMentions();
        for(Status theStatus : responses) {
            if (theStatus.getText().contains("let's play")&&!players.contains(theStatus.getUser().getScreenName())&&!statusesToExclude.contains(theStatus)) {
                toReturn.add(theStatus);
            }
        }
        return toReturn;
    }

}
