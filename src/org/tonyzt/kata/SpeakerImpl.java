package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 16/10/11
 * Time: 0.24
 * To change this template use File | Settings | File Templates.
 */
public class SpeakerImpl implements Speaker {
     public void askToThinkAboutAnAnimal(OutStream dataWriter) {
        dataWriter.output("think of an animal");
    }

    public String askIfIsThisOne(String animal) {
        return "Is it a "+animal+"?";
    }

    public String ask(String question) {
        return question;
    }

    public String askWhatAnimalWas() {
        return "What animal was?";
    }

    public String no() {
        return "no";
    }

    public String yes() {
        return "yes";
    }

    public void askAnswer(OutStream outStream, String question, String animal1, String animal2) {
        outStream.output("What should be the answer to the question \""+question+"\" "+"to indicate a "+animal1+" compared to a "+animal2+"?");
    }

    public String remindYesOrNot() {
        return "yes or not";
}

    public String askWhatIsTheDiscriminatingQuestion(String animal1, String animal2) {
       return "What question would you suggest to distinguish a "+animal1+" from a "+animal2+"?";
    }
}
