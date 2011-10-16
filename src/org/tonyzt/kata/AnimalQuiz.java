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
    private OutStream _dataWriter;
    private InStream _dataReader;
    private StateContext _sc;
    private NodeI _knowledgeTree;
    private Speaker _speaker;

    public AnimalQuiz(InStream inputData, OutStream writer,NodeI node) {
        _dataReader =inputData;
        _knowledgeTree = node;
        _dataWriter = writer;
    }

    public AnimalQuiz(InStream inputData, OutStream writer,NodeI node,StateContext sc) {
        _dataReader =inputData;
        _knowledgeTree = node;
        _dataWriter = writer;
        _sc = sc;
    }

    public AnimalQuiz(Speaker speaker,InStream inputData, OutStream writer,NodeI node,StateContext sc) {
        _speaker = speaker;
        _dataReader =inputData;
        _knowledgeTree = node;
        _dataWriter = writer;
        _sc =sc;
    }

    private AnimalQuiz() {
        this( new SpeakerImpl(),new InStream() {
                    Scanner scanner = new Scanner(System.in);
                    public String getInput() {
                        return scanner.nextLine();
                    }
                }, new OutStream(){
                    public void output(String out) {
                        System.out.println(out);
                    }
                    public void close() {
                    }
                }, new LeafNode("elephant"),
                new StateContext());
    }

    public static void main(String[] string) {
        AnimalQuiz animalQuiz = new AnimalQuiz();
        animalQuiz.start();
        while(true) {
            animalQuiz.step();
        }
    }

    public Speaker getSpeaker() {
        return _speaker;
    }

    public void start() {
        _sc = new StateContext();
        _speaker.askToThinkAboutAnAnimal(_dataWriter);
        //Conversator.getInstance().askToThinkAboutAnAnimal(_dataWriter);
    }

    public void step() {
        _sc.step(this, _dataReader, _dataWriter);
    }

    private NodeI getKnowledgeTree() {
        return _knowledgeTree;
    }

    private NodeI currentNode;
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

    public NodeI getNode() {
        return _knowledgeTree;
    }


    public void addKnowledge(List<String> yesNoList, String question, String answer, String animal) {
        NodeI node = _knowledgeTree.arrangeKnowledge(yesNoList, animal, question, answer);
        _knowledgeTree =node;
    }

    public void conversate(StateContext sc, OutStream outStream) {
        //getKnowledgeTree().conversate(sc, outStream);
        getKnowledgeTree().conversate(this.getSpeaker(),sc,outStream);
    }

    public void reAlignCurrentNodeToRoot() {
       setCurrentNode(this.getKnowledgeTree());
    }
}

