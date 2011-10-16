package test.org.tonyxzt.kata.spike;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 02/07/11
 * Time: 19.43
 * To change this template use File | Settings | File Templates.
 */
public class TwitterMessageGetter {
    String user;
    Twitter twitter;
    public TwitterMessageGetter(Twitter twitter, String user) {
        this.user = user;
        this.twitter = twitter;
    }

    public String lastMessage() throws Exception {
        ResponseList<Status> responses =  twitter.getMentions();

        for(Status status: responses) {
            if (status.getUser().getScreenName().equals(user))
                return status.getText();
        }

        return "";
    }


    public List<String> lastMessages() throws Exception {
        List<String> messages = new ArrayList<String>();
        ResponseList<Status> responses =  twitter.getMentions();

        for(Status status: responses) {
            if (status.getUser().getScreenName().equals(user))
                messages.add(status.getText());
            }
        return messages;
    }

    public List<String> lastMessages(List<String> excludeList) throws Exception {
        List<String> messages = new ArrayList<String>();
        ResponseList<Status> responses =  twitter.getMentions();

        for(Status status: responses) {
            if (status.getUser().getScreenName().equals(user))
                if (!excludeList.contains(status.getText()))
                messages.add(status.getText());
        }
        return messages;
    }
}
