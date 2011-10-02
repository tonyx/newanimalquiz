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

    NodeI currentNode;
    public NodeI getCurrentNode() {
        return currentNode;
    }
    public void setCurrentNode(NodeI currentNode) {
        this.currentNode = currentNode;
    }

    NodeI knowledgeTree;
    public NodeI getKnowledgeTree() {
        return knowledgeTree;
    }

    public void setKnowledgeTree(NodeI knowledgeTree) {
        this.knowledgeTree = knowledgeTree;
    }

    public void addKnowledge(List<String> yesNoList, String question, String answer, String animal) {
        NodeI nod = getKnowledgeTree().arrangeByPath(yesNoList, animal, question, answer);
        knowledgeTree =nod;
    }
}
