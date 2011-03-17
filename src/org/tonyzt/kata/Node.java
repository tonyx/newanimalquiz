package org.tonyzt.kata;

import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 27/02/11
 * Time: 2.17
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private String question;
    public void setQuestion(String question) {
        this.question = question;
    }


    private String animal;
    public void setAnimal(String animal) {
        this.animal = animal;
    }


    private Node yesBranch;
    public void setYesBranch(Node yesBranch) {
        this.yesBranch = yesBranch;
    }

    private Node noBranch;
    public void setNoBranch(Node noBranch) {
        this.noBranch = noBranch;
    }


    private boolean isLeaf = true;
    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }


    public Node(String question, Node yesBranch, Node noBranch) {
        this.question=question;
        this.setLeaf(false);
        this.setYesBranch(yesBranch);
        this.setNoBranch(noBranch);
    }

    public Node() {

    }

    @Override
    public String toString() {
        return "Node{" +
                (!"".equals(question)&&question!=null? "question='" + question + '\'':"") + " "+
                (!"".equals(animal)&&animal!=null? "animal='" + animal + '\'':"") + " "+
                (yesBranch!=null? ", yesBranch=" + yesBranch :"")+ " "+
                (noBranch!=null? ", noBranch=" + noBranch :"")+ " "+
//                ", isLeaf=" + isLeaf +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (isLeaf != node.isLeaf) return false;
        if (animal != null ? !animal.equals(node.animal) : node.animal != null) return false;
        if (noBranch != null ? !noBranch.equals(node.noBranch) : node.noBranch != null) return false;
        if (question != null ? !question.equals(node.question) : node.question != null) return false;
        if (yesBranch != null ? !yesBranch.equals(node.yesBranch) : node.yesBranch != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (animal != null ? animal.hashCode() : 0);
        result = 31 * result + (yesBranch != null ? yesBranch.hashCode() : 0);
        result = 31 * result + (noBranch != null ? noBranch.hashCode() : 0);
        result = 31 * result + (isLeaf ? 1 : 0);
        return result;
    }

    public Node arrangeByPath(List<String> strings, String newAnimal, String question, String yesNoAnswer) {
        if (strings.size()==0) {
            Node node = (yesNoAnswer.equalsIgnoreCase("no")?new Node(question,new Node(this.animal),new Node(newAnimal)):
                new Node(question,new Node(newAnimal), new Node(this.animal)));
            return node;
        }

        List<String> tail = (strings.size()>=1?strings.subList(1,strings.size()):new ArrayList<String>());
        String head = (strings.size()>0?strings.get(0):"");

        return (head.equalsIgnoreCase("Yes")? new Node(this.question,this.getYesBranch().arrangeByPath(tail,newAnimal,question,yesNoAnswer),this.getNoBranch()):
                new Node(this.question,this.getYesBranch(),this.getNoBranch().arrangeByPath(tail,newAnimal,question,yesNoAnswer)));
    }
}

