package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 27/02/11
 * Time: 12.29
 * To change this template use File | Settings | File Templates.
 */
public interface NodeI {
    public boolean isLeaf();
    public String getQuestion();
    public NodeI getYesNode();
    public NodeI getLeftNode();

}
