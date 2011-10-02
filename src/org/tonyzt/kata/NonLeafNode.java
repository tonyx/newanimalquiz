package org.tonyzt.kata;

import org.tonyzt.kata.states.Guessing;
import org.tonyzt.kata.states.StateContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 01/10/11
 * Time: 13.34
 * To change this template use File | Settings | File Templates.
 */
public class NonLeafNode implements NodeI {
    private String question;
    private NodeI yesBranch;
    public void conversate(StateContext sc, OutStream outStream) {
        outStream.output(getQuestion());
        sc.setState(new Guessing());
    }

    private NodeI noBranch;
    public NonLeafNode(String question, NodeI yesBranch, NodeI noBranch) {
        this.question=question;
        this.yesBranch = yesBranch;
        this.noBranch=noBranch;
    }

    @Override
    public String toString() {
        return "LeafNode{" +
                (!"".equals(question)&&question!=null? "question='" + question + '\'':"") + " "+
                (yesBranch!=null? ", yesBranch=" + yesBranch :"")+ " "+
                (noBranch!=null? ", noBranch=" + noBranch :"")+ " "+
                '}';
    }

    public String getAnimal() {
        return null;
    }

    @Override
    public NodeI getSubBranch(String yesNot)
    {
        if ("yes".equalsIgnoreCase(yesNot))
            return getYesBranch();
        if ("no".equalsIgnoreCase(yesNot))
            return getNoBranch();
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NonLeafNode)) return false;

        NonLeafNode node = (NonLeafNode) o;

        if (noBranch != null ? !noBranch.equals(node.noBranch) : node.noBranch != null) return false;
        if (question != null ? !question.equals(node.question) : node.question != null) return false;
        if (yesBranch != null ? !yesBranch.equals(node.yesBranch) : node.yesBranch != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (yesBranch != null ? yesBranch.hashCode() : 0);
        result = 31 * result + (noBranch != null ? noBranch.hashCode() : 0);
        return result;
    }

    public NodeI arrangeByPath(List<String> strings, String newAnimal, String question, String yesNoAnswer) {
        List<String> tail = (strings.size()>=1?strings.subList(1,strings.size()):new ArrayList<String>());
        String head = (strings.size()>0?strings.get(0):"");
        return (head.equalsIgnoreCase("Yes")? new NonLeafNode(this.getQuestion(),this.getYesBranch().arrangeByPath(tail, newAnimal, question, yesNoAnswer),this.getNoBranch()):
                new NonLeafNode(this.getQuestion(),this.getYesBranch(),this.getNoBranch().arrangeByPath(tail,newAnimal,question,yesNoAnswer)));
    }

    private String getQuestion() {
        return question;
    }

    private NodeI getYesBranch() {
        return yesBranch;
    }

    private NodeI getNoBranch() {
        return noBranch;
    }
}

