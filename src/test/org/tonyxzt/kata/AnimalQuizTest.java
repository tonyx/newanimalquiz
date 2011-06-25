package test.org.tonyxzt.kata;

import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.stubbing.OngoingStubbing;
import org.tonyzt.kata.*;
import org.tonyzt.kata.persistency.AnimalQuizStorage;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/02/11
 * Time: 1.19
 * To change this template use File | Settings | File Templates.
 */
public class AnimalQuizTest {
    OutStream writer;
    InStream inputData;

    @Before
    public void SetUp() {
        writer = mock(OutStream.class);
        inputData = mock(InStream.class);
    }


    @Test
    public void when_starting_state_is_elefant_then_first_question_is_if_it_is_elefant() {
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, "elephant");
        InOrder inOrder = inOrder(writer);
        animalQuiz.start();
        animalQuiz.step();
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it a elephant?");
    }


    @Test
    public void can_distinguish_between_elephant_and_mouse() {
        Node root = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        when(inputData.getInput()).thenReturn("No");
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,writer,root);
        InOrder inOrder = inOrder(writer);

        animalQuiz.start();
        animalQuiz.step();
        animalQuiz.step();

        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Is it a mouse?");
    }

    @Test
    public void testOneLevelLearning() {
        Node root = new Node("elephant");
        Node expectedAfterLearning = new Node("Is it big?",new Node("elephant"),new Node("mouse"));

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,writer,root);
        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it big?").thenReturn("No");
        InOrder inOrder = inOrder(writer);

        animalQuiz.start();
        stepNTimes(animalQuiz, 5);

        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it a elephant?"); //no
        inOrder.verify(writer).output("What animal was?"); //mouse
        inOrder.verify(writer).output("What question would you suggest to distinguish a elephant from a mouse?"); // is it big?
        inOrder.verify(writer).output("What should be the answer to the question \"Is it big?\" to indicate a mouse compared to a elephant?"); //No

        Assert.assertEquals(expectedAfterLearning, animalQuiz.getNode());
    }

    @Test
    public void testTwoLevelLearning() {
        Node startNode = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        Node expected = new Node("Is it big?",new Node("elephant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,writer,startNode);
        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        InOrder inOrder = inOrder(writer);

        animalQuiz.start();
        stepNTimes(animalQuiz,5);

        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Is it a mouse?");
        inOrder.verify(writer).output("What animal was?");
        inOrder.verify(writer).output("What question would you suggest to distinguish a mouse from a worm?");
        inOrder.verify(writer).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");

        Assert.assertEquals(expected, animalQuiz.getNode());
    }


    @Test
    public void testThreeLevelLearning() {
        Node startNode = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        Node expected = new Node("Is it big?",new Node("elephant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,writer,startNode);

        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        animalQuiz.start();
        stepNTimes(animalQuiz,6);
        InOrder inOrder = inOrder(writer);

        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("What animal was?");
        inOrder.verify(writer).output("What question would you suggest to distinguish a mouse from a worm?");
        inOrder.verify(writer).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");
        Assert.assertEquals(expected, animalQuiz.getNode());

        when(inputData.getInput()).thenReturn("No").thenReturn("Yes").thenReturn("No").thenReturn("microb").thenReturn("Is it microscopic?").thenReturn("Yes");

        stepNTimes(animalQuiz,5);

        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Does it have 1000 legs?");
        inOrder.verify(writer).output("Is it a worm?");
        inOrder.verify(writer).output("What animal was?");
        inOrder.verify(writer).output("What question would you suggest to distinguish a worm from a microb?");
        verify(writer).output("What should be the answer to the question \"Is it microscopic?\" to indicate a microb compared to a worm?");
    }


    @Test
    public void testPersistence()
    {
        NodeI node = new Node("Is it big?",new Node("elephant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        AnimalQuizStorage animalQuizStorage = new AnimalQuizStorage();

        animalQuizStorage.persist(node,"name");

        NodeI retrieved = animalQuizStorage.load("name");
        Assert.assertEquals(node,retrieved);
    }


    @Test
    public void testOneLevelLearningOnDomainModel() {
        Node root = new Node("elephant");
        Node expectedAfterLearning = new Node("Is it big?",new Node("elephant"),new Node("mouse"));

        List<String> aList = new ArrayList<String>();
        aList.add("No");
        Node nodeResulted = root.arrangeByPath(new ArrayList<String>(),"mouse","Is it big?","No");
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }

    @Test
    public void testOneLevelLearningOnTree2() {
        Node root = new Node("elephant");
        Node expectedAfterLearning = new Node("Is it small?",new Node("mouse"),new Node("elephant"));

        List<String> aList = new ArrayList<String>();
        aList.add("No");
        Node nodeResulted = root.arrangeByPath(new ArrayList<String>(),"mouse","Is it small?","Yes");
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testTwoLevelLearningOnTree() {
        Node node = new Node("Is it small?",new Node("mouse"),new Node("elephant"));
        Node expectedAfterLearning = new Node("Is it small?",new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")),
                new Node("elephant"));

        List<String> aList = new ArrayList<String>();
        aList.add("Yes");
        Node nodeResulted = node.arrangeByPath(aList,"worm","Does it have 1000 legs?","Yes");
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testThreeLevelLearningOnTree() {
       Node node = new Node("Is it small?",new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")),
                new Node("elephant"));
        Node expectedAfterLearning = new Node("Is it small?",new Node("Does it have 1000 legs?",new Node("Is it microscopic?",new Node("microb"),new Node("worm")),new Node("mouse")),
                new Node("elephant"));
        List<String> aList = new ArrayList<String>();
        aList.add("Yes");
        aList.add("Yes");
        Node nodeResulted = node.arrangeByPath(aList, "microb", "Is it microscopic?", "Yes");
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testNodeEquality() {
        Node node1 = new Node();
        Node node2 = new Node();
        Assert.assertEquals(node1,node2);
    }

    @Test
    public void testNodeEquality2() {
        Node root = new Node("Is it big?",new Node("elefant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        Node root2 = new Node("Is it big?",new Node("elefant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        Assert.assertEquals(root,root2);
    }




    private void stepNTimes(AnimalQuiz animalQuiz, int n) {
        for (int i=0;i<n;i++) {
            animalQuiz.step();
        }
    }


}



