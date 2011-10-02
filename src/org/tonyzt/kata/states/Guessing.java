package org.tonyzt.kata.states;

import org.tonyzt.kata.*;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.34
 * To change this template use File | Settings | File Templates.
 */
public class Guessing implements State {
    @Override
    public void step(StateContext sc, AnimalQuiz animalQuiz, InStream instream, OutStream outStream) {
        String answer = instream.getInput();
        sc.getYesNoList().add(answer);

        NodeI node = animalQuiz.getCurrentNode().getSubBranch(answer);

        node.conversate(sc, outStream);
        animalQuiz.setCurrentNode(node);
    }
}
