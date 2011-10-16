package org.tonyzt.kata;

import org.tonyzt.kata.states.StateContext;

import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 27/02/11
 * Time: 12.29
 * To change this template use File | Settings | File Templates.
 */
public interface NodeI  {
    //public void conversate(StateContext stateContext, OutStream outStream);
    public void conversate(Speaker speaker, StateContext stateContext, OutStream outStream);
    public NodeI getSubBranch(String yesNot);
    public NodeI arrangeKnowledge(List<String> yesNoList, String question, String answer, String animal);
    public String getAnimal();
}
