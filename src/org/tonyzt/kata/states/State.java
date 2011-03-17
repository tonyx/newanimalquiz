package org.tonyzt.kata.states;

import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.OutStream;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.20
 * To change this template use File | Settings | File Templates.
 */
public interface State {
    public void step(StateContext sc, AnimalQuiz animalQuiz, InStream instream, OutStream outStream);
}
