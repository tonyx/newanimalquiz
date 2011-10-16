package test.org.tonyxzt.kata;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 03/07/11
 * Time: 18.10
 * To change this template use File | Settings | File Templates.
 */
public class TwitterConversationWith {
    Twitter twitter;
    String screenNamePlayer;
    public TwitterConversationWith(Twitter twitter, String twitterScreenName) {
        this.twitter = twitter;
        this.screenNamePlayer=twitterScreenName;
    }

    @Deprecated
    public List<String> getLastMessages() throws TwitterException {
        List<String> toReturn = new ArrayList<String>();
            for(Status status: twitter.getMentions()) {
                if (status.getUser().getScreenName().equals(screenNamePlayer))
                    toReturn.add(status.getText());
            }
        return toReturn;
    }

    public List<Status> getLastStatusMessages() throws TwitterException {
        List<Status> toReturn = new ArrayList<Status>();
        for(Status status: twitter.getMentions()) {
            if (status.getUser().getScreenName().equals(screenNamePlayer))
                toReturn.add(status);
        }

        return toReturn;  //To change body of created methods use File | Settings | File Templates.
    }

    public List<Status> getLastStatusMessages(List<Status> except) throws TwitterException {
        List<Status> toReturn = new ArrayList<Status>();
        for(Status status: twitter.getMentions()) {
            if (status.getUser().getScreenName().equals(screenNamePlayer))
                if (!except.contains(status))
                    toReturn.add(status);
        }
        return toReturn;  //To change body of created methods use File | Settings | File Templates.
    }

    public String getLastSingleStatusMessages(List<Status> statuses) throws TwitterException {
        int timeouts=9;
        List<Status> toReturn = new ArrayList<Status>();
        while (true) {
            for(Status status: twitter.getMentions()) {
                if (status.getUser().getScreenName().equals(screenNamePlayer)) {
                    if (!statuses.contains(status)) {
                        toReturn.add(status);
                        statuses.add(status);
                    }
                }
            }

        //while(true) {
            if (toReturn.size() > 0) {
                return (Collections.max(toReturn, new Comparator<Status>() {
                    @Override
                    public int compare(Status o1, Status o2) {
                        return (int) (o1.getCreatedAt().compareTo(o2.getCreatedAt()));
                    }
                })).getText().replace("@AnimalQuiz ","");
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                System.out.print(e.toString());
            }
            continue;
        }
    }
}
