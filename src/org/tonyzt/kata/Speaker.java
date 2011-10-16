package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 16/10/11
 * Time: 0.17
 * To change this template use File | Settings | File Templates.
 */
public interface Speaker {
    void askToThinkAboutAnAnimal(OutStream dataWriter);

    String askIfIsThisOne(String animal);

    String ask(String question);

    String askWhatAnimalWas();

    String no();

    String yes();

    void askAnswer(OutStream outStream, String question, String animal1, String animal2);

    String remindYesOrNot();

    String askWhatIsTheDiscriminatingQuestion(String animal1, String animal2);
}
