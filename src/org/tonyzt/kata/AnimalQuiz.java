package org.tonyzt.kata;
import org.tonyzt.kata.states.StateContext;

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
    OutStream _writer;
    InStream _inputData;
    StateContext sc;
    Node knowelegeTree;

    public Node getKnowelegeTree() {
        return knowelegeTree;
    }

    Node currentNode;
    public Node getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    String thoughtAnimal;
    public void setThoughtAnimal(String thoughtAnimal) {
        this.thoughtAnimal = thoughtAnimal;
    }
    public String getThoughtAnimal() {
        return thoughtAnimal;
    }

    public AnimalQuiz(InStream inputData, OutStream writer,String animal) {
        _inputData=inputData;
        knowelegeTree = new Node(animal);
        _writer = writer;
    }

    public AnimalQuiz(InStream inputData, OutStream writer,Node node) {
        _inputData=inputData;
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
        sc = new StateContext();
 //       System.out.println("sc"+sc);
        //sc.getAnimalKnowledgeManager().setKnowledgeTree(new Node("elephant"));
        //sc.getAnimalKnowledgeManager().getKnowledgeTree().setLeaf(true);

        knowelegeTree= new Node("elephant");
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
        sc.step(this,_inputData,_writer);
    }

    public void addKnowledge(List<String> yesNoList, String question, String answer, String animal) {
        Node node = knowelegeTree.arrangeByPath(yesNoList,animal,question,answer);
        knowelegeTree=node;
    }

    public void start() {
        sc = new StateContext();
        _writer.output("think of an animal");
    }
}

