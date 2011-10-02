package org.tonyzt.kata;
import org.tonyzt.kata.states.StateContext;

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
    NodeI knowledgeTree;

    public NodeI getKnowledgeTree() {
        return knowledgeTree;
    }

    NodeI currentNode;
    public NodeI getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(NodeI currentNode) {
        this.currentNode = currentNode;
    }

    String thoughtAnimal;
    public void setThoughtAnimal(String thoughtAnimal) {
        this.thoughtAnimal = thoughtAnimal;
    }

    public String getThoughtAnimal() {
        return thoughtAnimal;
    }

    public AnimalQuiz(InStream inputData, OutStream writer,NodeI node) {
        _inputData=inputData;
        knowledgeTree = node;
        _writer = writer;
    }

    public NodeI getNode() {
        return knowledgeTree;
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
        knowledgeTree = new LeafNode("elephant");
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
        NodeI node = knowledgeTree.arrangeByPath(yesNoList,animal,question,answer);
        knowledgeTree =node;
    }

    public void start() {
        sc = new StateContext();
        _writer.output("think of an animal");
    }
}

