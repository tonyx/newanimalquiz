package org.tonyzt.kata.states;

import org.tonyzt.kata.AnimalKnowledgeManager;
import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.OutStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 14/03/11
 * Time: 15.22
 * To change this template use File | Settings | File Templates.
 */
public class StateContext {
    AnimalKnowledgeManager animalKnowledgeManager;
    public AnimalKnowledgeManager getAnimalKnowledgeManager() {
        return animalKnowledgeManager;
    }

    public void setAnimalKnowledgeManager(AnimalKnowledgeManager animalKnowledgeManager) {
        this.animalKnowledgeManager = animalKnowledgeManager;
    }

    List<String> yesNoList;
    public List<String> getYesNoList() {
        return yesNoList;
    }

    public void resetYesNoList() {
        yesNoList = new ArrayList<String>();
    }

    public void setYesNoList(List<String> yesNoList) {
        this.yesNoList = yesNoList;
    }

    String question;
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getQuestion() {
        return question;
    }

    private State _state;
    public StateContext() {
        this._state = new Started();
    }

    public void setState(State state) {
        _state=state;
    }

    public void step(AnimalQuiz animalQuiz, InStream inStream, OutStream outStream) {
        _state.step(this,animalQuiz, inStream, outStream);
    }
}

