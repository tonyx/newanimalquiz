package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 16/10/11
 * Time: 20.13
 * To change this template use File | Settings | File Templates.
 */
public class SpeakerImplIt implements Speaker {
      public void askToThinkAboutAnAnimal(OutStream dataWriter) {
        dataWriter.output("Pensa ad un animale");
    }

    public String askIfIsThisOne(String animal) {
        return "E' un "+animal+"?";
    }

    public String ask(String question) {
        return question;
    }

    public String askWhatAnimalWas() {
        return "Che animale era?";
    }

    public String no() {
        return "no";
    }

    public String yes() {
        return "si";
    }

    public void askAnswer(OutStream outStream, String question, String animal1, String animal2) {
        //outStream.output("What should be the answer to the question \""+question+"\" "+"to indicate a "+animal1+" compared to a "+animal2+"?");
        outStream.output("Quale dovrebbe essere la risposta alla domanda \""+question+"\" "+" per indicare "+animal1+" confrontato con un "+animal2+"?");
    }

    public String remindYesOrNot() {
        return "si o no";
}

    public String askWhatIsTheDiscriminatingQuestion(String animal1, String animal2) {
       //return "What question would you suggest to distinguish a "+animal1+" from a "+animal2+"?";
       return "Quale domanda suggeriresti per distinguere un "+animal1+"da un "+animal2;
    }
}
