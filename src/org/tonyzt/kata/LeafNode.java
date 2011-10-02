package org.tonyzt.kata;

import org.tonyzt.kata.states.GuessMade;
import org.tonyzt.kata.states.StateContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 01/10/11
 * Time: 12.58
 * To change this template use File | Settings | File Templates.
 */
public class LeafNode implements NodeI {
    private String animal;

    public void conversate(StateContext sc, OutStream outStream) {
        outStream.output("Is it a " + getAnimal() + "?");
        sc.setState(new GuessMade());
    }

    @Override
    public String toString() {
        return "LeafNode{" + (!"".equals(animal)&&animal!=null? "animal='" + animal + '\'':"") + " "+ '}';
    }

    public LeafNode(String animal) {
        this.animal=animal;
    }

    public String getAnimal() {
        return animal;
    }

    public NodeI getSubBranch(String yesNot)
    {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeafNode)) return false;
        LeafNode node = (LeafNode) o;
        if (animal != null ? !animal.equals(node.animal) : node.animal != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (animal != null ? animal.hashCode() : 0);
        return result;
    }

    public NodeI arrangeByPath(List<String> strings, String newAnimal, String question, String yesNoAnswer) {
        NodeI node = (yesNoAnswer.equalsIgnoreCase("no")?new NonLeafNode(question,new LeafNode(this.animal),new LeafNode(newAnimal)):
                new NonLeafNode(question,new LeafNode(newAnimal), new LeafNode(this.animal)));
        return node;
    }
}
