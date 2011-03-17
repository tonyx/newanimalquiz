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
        //animalQuiz.getAnswersList().add(answer);
        sc.getYesNoList().add(answer);

        Node node = null;
        if ("no".equalsIgnoreCase(answer)) {
            node = animalQuiz.getCurrentNode().getNoBranch();
        } else if ("yes".equalsIgnoreCase(answer)) {
            node = animalQuiz.getCurrentNode().getYesBranch();
        } else
            return;
        if (node.isLeaf()) {
            outStream.output("Is it a " + node.getAnimal() + "?");
            sc.setState(new GuessMade());
            animalQuiz.setCurrentNode(node);

            //sc.getAnimalKnowledgeManager().
        } else {
            outStream.output(node.getQuestion());
            animalQuiz.setCurrentNode(node);
        }
    }
}
