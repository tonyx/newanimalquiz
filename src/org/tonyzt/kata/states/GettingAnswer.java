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
        animalQuiz.getSpeaker().askAnswer(outStream, sc.getQuestion(), animalQuiz.getThoughtAnimal(), animalQuiz.getCurrentNode().getAnimal());
        //Conversator.getInstance().askAnswer(outStream, sc.getQuestion(), animalQuiz.getThoughtAnimal(), animalQuiz.getCurrentNode().getAnimal());
        String answer = instream.getInput();
        animalQuiz.addKnowledge(sc.getYesNoList(), sc.getQuestion(), answer, animalQuiz.getThoughtAnimal());
        sc.setState(new Started());
    }
}
