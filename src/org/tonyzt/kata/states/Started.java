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
//        animalQuiz.setAnswersList(new ArrayList<String>());
        //sc.setYesNoList(new ArrayList<String>());
        sc.resetYesNoList();
        animalQuiz.setCurrentNode(animalQuiz.getKnowelegeTree());

        // ref
//        sc.animalKnowledgeManager.setCurrentNode(sc.animalKnowledgeManager.getCurrentNode());

//        if (animalQuiz.getKnowelegeTree().isLeaf()) {
//            outStream.output("Is it a "+animalQuiz.getKnowelegeTree().getAnimal()+"?");
//            sc.setState(new GuessMade());
//        } else {
//            sc.setState(new Guessing());
//            outStream.output(animalQuiz.getKnowelegeTree().getQuestion());
//        }

        // refactor in progress
        if (animalQuiz.getKnowelegeTree().isLeaf()) {
            String animal = animalQuiz.getKnowelegeTree().getAnimal();
           // animal = sc.getAnimalKnowledgeManager().getKnowledgeTree().getAnimal();
            //outStream.output("Is it a "+animalQuiz.getKnowelegeTree().getAnimal()+"?");
            outStream.output("Is it a "+animal+"?");
            sc.setState(new GuessMade());
        } else {
            sc.setState(new Guessing());
            outStream.output(animalQuiz.getKnowelegeTree().getQuestion());
        }





    }
}
