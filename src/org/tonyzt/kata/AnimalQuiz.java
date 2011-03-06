package org.tonyzt.kata;
import java.util.Scanner;


/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/02/11
 * Time: 1.07
 * To change this template use File | Settings | File Templates.
 */
public class AnimalQuiz {
    Node knowelegeTree;
    OutStream _writer;
    InStream _inputData;
    String _animal;
    String thoughtAnimal;
    String question;
    String answer;

    private String state = "NOT_STARTED";

    public AnimalQuiz(InStream inputData, OutStream writer,String animal) {
        _inputData=inputData;
        _animal=animal;
        knowelegeTree = new Node(_animal);
        _writer = writer;
    }

    public AnimalQuiz(InStream inputData, OutStream writer,Node node) {
        _inputData=inputData;
        _animal=node.getAnimal();
        knowelegeTree = node;
        _writer = writer;
    }

    @Deprecated
    private AnimalQuiz() {
        _writer = new OutStream(){
            public void output(String out) {
                System.out.println(out);
            }
            public void close() {
            }
        };
        _inputData = new InStream() {
            Scanner scanner = new Scanner(System.in);
            public String getInput() {
                String toReturn = scanner.nextLine();
                return toReturn;
            }
        };
        knowelegeTree= new Node("elefant");
        _animal="elefant";
        knowelegeTree.setLeaf(true);
    }

    public static void main(String[] string) {

        AnimalQuiz animalQuiz = new AnimalQuiz();
        animalQuiz.start();
        while(true) {
            animalQuiz.step();
        }
    }

    public void step() {
        if ("STARTED".equals(state)) {
            System.out.println("started");
            if (knowelegeTree.isLeaf()) {
                _writer.output("Is it a "+knowelegeTree.getAnimal()+"?");
                state="GUESS_MADE";
            } else {
                state="GUESSING";
                _writer.output(knowelegeTree.getQuestion());
            }
            return;
        }
        if ("GUESSING".equals(state)) {
            String answer =_inputData.getInput();
            Node node = ("no".equalsIgnoreCase(answer)?knowelegeTree.getNoBranch():knowelegeTree.getYesBranch());
            if (node.isLeaf())  {
                _writer.output("Is it a "+node.getAnimal()+"?");
                state = "GUESS_MADE";
            } else {
                _writer.output(node.getQuestion());
                knowelegeTree= node;
            }
        }
        if ("GUESS_MADE".equals(state)) {
            String confirmation = this._inputData.getInput();
            if ("No".equalsIgnoreCase(confirmation)) {
                _writer.output("What animal was?");
                thoughtAnimal = _inputData.getInput();
                state = "THOUGHT_ANIMAL_STORED";
            } else {
                System.out.println("yeah");
                state="STARTED";
            }
        }
        if ("THOUGHT_ANIMAL_STORED".equals(state)) {
            _writer.output("What question would you suggest to distinguish a "+knowelegeTree.getAnimal()+" from a "+thoughtAnimal+"?");
            state = "GETTING_QUESTION";
        }
        if ("GETTING_QUESTION".equals(state)) {
            question = _inputData.getInput();
            state = "GETTING_ANSWER";
        }
        if ("GETTING_ANSWER".equals(state)) {
            _writer.output("What should be the answer to the question \""+question+"\" "+"to indicate a "+thoughtAnimal+" compared to a "+knowelegeTree.getAnimal()+"?");
            answer = _inputData.getInput();
            state = "ADDING_KNOWELEGE";
            add_knowlege(question,answer,thoughtAnimal);
            state = "STARTED";
        }
    }

    private void add_knowlege(String question, String answer, String animal)
    {
        System.out.println("adding knowelege");
        Node node = new Node(animal);
        Node oldNode = new Node(knowelegeTree.getAnimal());
        knowelegeTree.setLeaf(false);
        knowelegeTree.setAnimal("");
        knowelegeTree.setQuestion(question);
        if ("yes".equalsIgnoreCase(answer.toLowerCase())) {
            knowelegeTree.setYesBranch(node);
            knowelegeTree.setNoBranch(oldNode);
        } else {
            knowelegeTree.setYesBranch(oldNode);
            knowelegeTree.setNoBranch(node);
        }
    }

    public void start() {
        state = "STARTED";
        _writer.output("think of an animal");
    }
}



