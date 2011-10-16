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
        animalQuiz.reAlignCurrentNodeToRoot();
        animalQuiz.conversate(sc,outStream);
    }
}
