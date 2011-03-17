package org.tonyzt.kata.states;

import org.tonyzt.kata.*;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.33
 * To change this template use File | Settings | File Templates.
 */
public class GuessMade implements State {
    @Override
    public void step(StateContext sc, AnimalQuiz animalQuiz, InStream instream, OutStream outStream) {
        String confirmation = instream.getInput();
        if ("no".equalsIgnoreCase(confirmation)) {
            outStream.output("What animal was?");
            animalQuiz.setThoughtAnimal(instream.getInput());
            sc.setState(new ThoughtAnimalStored());
        } else if ("yes".equalsIgnoreCase(confirmation)) {
            outStream.output("yeah");
            sc.setState(new Started());
        } else outStream.output("yes or not");
    }
}
