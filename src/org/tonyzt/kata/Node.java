package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 27/02/11
 * Time: 2.17
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    private String animal;

    public void setYesBranch(Node yesBranch) {
        this.yesBranch = yesBranch;
    }

    public void setNoBranch(Node noBranch) {
        this.noBranch = noBranch;
    }

    private Node yesBranch;
    private Node noBranch;

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    private boolean isLeaf = true;
    public Node() {
    }

    public Node(String animal) {
        isLeaf=true;
        this.animal=animal;
    }
    public boolean isLeaf() {
        return isLeaf;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnimal() {
        return animal;
    }

    public Node getYesBranch() {
        return yesBranch;
    }

    public Node getNoBranch() {
        return noBranch;
    }

}
