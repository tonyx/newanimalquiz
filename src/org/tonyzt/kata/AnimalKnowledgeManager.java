package org.tonyzt.kata;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 15/03/11
 * Time: 17.13
 * To change this template use File | Settings | File Templates.
 */
public class AnimalKnowledgeManager {

    Node currentNode;
    public Node getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    Node knowledgeTree;
    public Node getKnowledgeTree() {
        return knowledgeTree;
    }

    public void setKnowledgeTree(Node knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    public void addKnowledge(List<String> yesNoList, String question, String answer, String animal) {
        Node nod = getKnowledgeTree().arrangeByPath(yesNoList, animal, question, answer);
        knowledgeTree =nod;
    }
}
