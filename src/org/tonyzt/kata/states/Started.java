package org.tonyzt.kata.states;

import org.tonyzt.kata.*;


/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.33
 * To change this template use File | Settings | File Templates.
 */
public class Started implements State {
    @Override
    public void step(StateContext sc, AnimalQuiz animalQuiz,InStream instream, OutStream outStream) {

        sc.resetYesNoList();
        animalQuiz.setCurrentNode(animalQuiz.getKnowledgeTree());

        animalQuiz.getKnowledgeTree().conversate(sc, outStream);
//        if (animalQuiz.getKnowledgeTree().isLeaf()) {
//            String animal = animalQuiz.getKnowledgeTree().getAnimal();
//            outStream.output("Is it a "+animal+"?");
//            sc.setState(new GuessMade());
//        } else {
//            sc.setState(new Guessing());
//            outStream.output(animalQuiz.getKnowledgeTree().getQuestion());
//        }

    }
}
