package org.tonyzt.kata.states;

import org.tonyzt.kata.*;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.36
 * To change this template use File | Settings | File Templates.
 */
public class GettingAnswer implements State {
    @Override
    public void step(StateContext sc, AnimalQuiz animalQuiz,InStream instream, OutStream outStream) {
        String question = sc.getQuestion();
        outStream.output("What should be the answer to the question \""+question+"\" "+"to indicate a "+animalQuiz.getThoughtAnimal()+" compared to a "+animalQuiz.getCurrentNode().getAnimal()+"?");
        String answer = instream.getInput();
        animalQuiz.addKnowledge(sc.getYesNoList(), question, answer, animalQuiz.getThoughtAnimal());
        sc.setState(new Started());
    }
}
