package org.tonyzt.kata.states;

import org.tonyzt.kata.*;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.35
 * To change this template use File | Settings | File Templates.
 */
public class ThoughtAnimalStored implements State {
    @Override
    public void step(StateContext sc, AnimalQuiz animalQuiz,InStream instream, OutStream outStream) {
        outStream.output(animalQuiz.getSpeaker().askWhatIsTheDiscriminatingQuestion(animalQuiz.getCurrentNode().getAnimal(),animalQuiz.getThoughtAnimal()));
        //outStream.output(Conversator.getInstance().askWhatIsTheDiscriminatingQuestion(animalQuiz.getCurrentNode().getAnimal(),animalQuiz.getThoughtAnimal()));
        String question = instream.getInput();
        sc.setQuestion(question);
        sc.setState(new GettingAnswer());
    }
}
