package org.tonyzt.kata;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/02/11
 * Time: 1.07
 * To change this template use File | Settings | File Templates.
 */
public class AnimalQuiz {
    private List<String> answersList = new ArrayList<String>();

    Node knowelegeTree;
    Node currentNode;

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

    public Node getNode() {
        return knowelegeTree;
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
            this.answersList=new ArrayList<String>();
            currentNode=knowelegeTree;
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
            this.answersList.add(answer);
            Node node = ("no".equalsIgnoreCase(answer)?currentNode.getNoBranch():currentNode.getYesBranch());
            if (node.isLeaf())  {
                _writer.output("Is it a "+node.getAnimal()+"?");
                state = "GUESS_MADE";
                currentNode= node;
            } else {
                _writer.output(node.getQuestion());
                currentNode= node;
            }
            return;
        }
        if ("GUESS_MADE".equals(state)) {
            String confirmation = this._inputData.getInput();
            if ("no".equalsIgnoreCase(confirmation)) {
                _writer.output("What animal was?");
                thoughtAnimal = _inputData.getInput();
                state = "THOUGHT_ANIMAL_STORED";
            } else {
                _writer.output("yeah");
                state="STARTED";
            }
            return;
        }
        if ("THOUGHT_ANIMAL_STORED".equals(state)) {
            _writer.output("What question would you suggest to distinguish a "+currentNode.getAnimal()+" from a "+thoughtAnimal+"?");
            question = _inputData.getInput();
            state = "GETTING_ANSWER";
            return;
        }
        if ("GETTING_QUESTION".equals(state)) {
            question = _inputData.getInput();
            state = "GETTING_ANSWER";
            return;
        }
        if ("GETTING_ANSWER".equals(state)) {
            _writer.output("What should be the answer to the question \""+question+"\" "+"to indicate a "+thoughtAnimal+" compared to a "+currentNode.getAnimal()+"?");
            answer = _inputData.getInput();
            state = "ADDING_KNOWELEGE";
            add_knowlege(question,answer,thoughtAnimal);
            state = "STARTED";
            return;
        }
    }

    private void add_knowlege(String question, String answer, String animal)
    {
        Node node = knowelegeTree.arrangeByPath(this.answersList,animal,question,answer);
        knowelegeTree=node;


//        Node node = new Node(animal);
//        Node oldNode = new Node(currentNode.getAnimal());
//        currentNode.setLeaf(false);
//        currentNode.setAnimal("");
//        currentNode.setQuestion(question);
//        if ("yes".equalsIgnoreCase(answer.toLowerCase())) {
//            currentNode.setYesBranch(node);
//            currentNode.setNoBranch(oldNode);
//        } else {
//            currentNode.setYesBranch(oldNode);
//            currentNode.setNoBranch(node);
//        }
    }

    public void start() {
        state = "STARTED";
        _writer.output("think of an animal");
    }

    @Override
    public String toString() {
        return "AnimalQuiz{" +
                "knowelegeTree=" + knowelegeTree +
                ", currentNode=" + currentNode +
                ", _writer=" + _writer +
                ", _inputData=" + _inputData +
                ", _animal='" + _animal + '\'' +
                ", thoughtAnimal='" + thoughtAnimal + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}



