package test.org.tonyxzt.kata;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.tonyzt.kata.*;
import org.tonyzt.kata.persistency.AnimalQuizStorage;
import org.tonyzt.kata.states.StateContext;

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
    InStream reader;


    @Test
    public void when_starting_state_is_elephant_then_first_question_is_if_it_is_elephant() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        //AnimalQuiz animalQuiz = new AnimalQuiz(reader, writer, new LeafNode("elephant"));
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,new LeafNode("elephant"),new StateContext());
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        animalQuiz.step();

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it a elephant?");
    }

    @Test
    public void can_distinguish_between_elephant_and_mouseXXxxx() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        when(reader.getInput()).thenReturn("No");
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,root,new StateContext());
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        animalQuiz.step();
        animalQuiz.step();

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Is it a mouse?");
    }

      @Test
    public void can_distinguish_between_elephant_and_mouseItalian() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new NonLeafNode("E' grande?",new LeafNode("elefante"),new LeafNode("topo"));
        when(reader.getInput()).thenReturn("No");
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImplIt(),reader,writer,root,new StateContext());
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        animalQuiz.step();
        animalQuiz.step();

        // then
        inOrder.verify(writer).output("Pensa ad un animale");
        inOrder.verify(writer).output("E' grande?");
        inOrder.verify(writer).output("E' un topo?");
    }


    @Test
    public void unallowedWordsInConversation() {

        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        when(reader.getInput()).thenReturn("burp").thenReturn("no").thenReturn("yes");
        //AnimalQuiz animalQuiz = new AnimalQuiz(reader,writer,root);
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,root,new StateContext());
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        animalQuiz.step();
        animalQuiz.step();
        animalQuiz.step();

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("yes or not");
        inOrder.verify(writer).output("Is it a mouse?");
    }


    @Test
    public void can_distinguish_between_elephant_and_mouse() {

        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        when(reader.getInput()).thenReturn("No");
        //AnimalQuiz animalQuiz = new AnimalQuiz(reader,writer,root);
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,root,new StateContext());
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        animalQuiz.step();
        animalQuiz.step();

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Is it a mouse?");
    }

    @Test
    public void testOneLevelLearning() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new LeafNode("elephant");
        NodeI expectedAfterLearning = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        //AnimalQuiz animalQuiz = new AnimalQuiz(reader,writer,root);
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,root,new StateContext());
        when(reader.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it big?").thenReturn("No");
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        stepNTimes(animalQuiz, 5);

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it a elephant?"); //no
        inOrder.verify(writer).output("What animal was?"); //mouse
        inOrder.verify(writer).output("What question would you suggest to distinguish a elephant from a mouse?"); // is it big?
        inOrder.verify(writer).output("What should be the answer to the question \"Is it big?\" to indicate a mouse compared to a elephant?"); //No

        Assert.assertEquals(expectedAfterLearning, animalQuiz.getNode());
    }

    @Test
    public void testTwoLevelLearning() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI startNode = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        NodeI expected = new NonLeafNode("Is it big?",new LeafNode("elephant"),new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")));
        //AnimalQuiz animalQuiz = new AnimalQuiz(reader,writer,startNode);
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,startNode,new StateContext());
        when(reader.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        InOrder inOrder = inOrder(writer);

        // when
        animalQuiz.start();
        stepNTimes(animalQuiz,5);

        // then
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
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI startNode = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        NodeI expected = new NonLeafNode("Is it big?",new LeafNode("elephant"),new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")));

        //AnimalQuiz animalQuiz = new AnimalQuiz(reader,writer,startNode);
        AnimalQuiz animalQuiz = new AnimalQuiz(new SpeakerImpl(),reader,writer,startNode,new StateContext());
        when(reader.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");

        // when
        animalQuiz.start();
        stepNTimes(animalQuiz,6);
        InOrder inOrder = inOrder(writer);

        // then
        inOrder.verify(writer).output("think of an animal");
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("What animal was?");
        inOrder.verify(writer).output("What question would you suggest to distinguish a mouse from a worm?");
        inOrder.verify(writer).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");
        Assert.assertEquals(expected, animalQuiz.getNode());

        // given
        when(reader.getInput()).thenReturn("No").thenReturn("Yes").thenReturn("No").thenReturn("microb").thenReturn("Is it microscopic?").thenReturn("Yes");

        // when
        stepNTimes(animalQuiz,5);

        // then
        inOrder.verify(writer).output("Is it big?");
        inOrder.verify(writer).output("Does it have 1000 legs?");
        inOrder.verify(writer).output("Is it a worm?");
        inOrder.verify(writer).output("What animal was?");
        inOrder.verify(writer).output("What question would you suggest to distinguish a worm from a microb?");
        verify(writer).output("What should be the answer to the question \"Is it microscopic?\" to indicate a microb compared to a worm?");
    }


    @Test
    @Ignore
    public void testPersistence()
    {
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI node = new NonLeafNode("Is it big?",new LeafNode("elephant"),new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")));
        AnimalQuizStorage animalQuizStorage = new AnimalQuizStorage();

        animalQuizStorage.persist(node,"name");

        NodeI retrieved = animalQuizStorage.load("name");
        Assert.assertEquals(node,retrieved);
    }


    @Test
    public void testOneLevelLearningOnDomainModel() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new LeafNode("elephant");
        NodeI expectedAfterLearning = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        List<String> aList = new ArrayList<String>();
        aList.add("No");

        // when
        NodeI nodeResulted = root.arrangeKnowledge(new ArrayList<String>(), "mouse", "Is it big?", "No");

        // then
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }

    @Test
    public void testEquality() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);

        //NodeI root = new LeafNode("elephant");
        NodeI expectedAfterLearning = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        NodeI expected = new NonLeafNode("Is it big?",new LeafNode("elephant"),new LeafNode("mouse"));
        List<String> aList = new ArrayList<String>();
        aList.add("No");

        // then
        Assert.assertEquals(expected,expectedAfterLearning);
    }



    @Test
    public void testOneLevelLearningOnTree2() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI root = new LeafNode("elephant");
        NodeI expectedAfterLearning = new NonLeafNode("Is it small?",new LeafNode("mouse"),new LeafNode("elephant"));
        List<String> aList = new ArrayList<String>();
        aList.add("No");

        // when
        NodeI nodeResulted = root.arrangeKnowledge(new ArrayList<String>(), "mouse", "Is it small?", "Yes");

        // then
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testTwoLevelLearningOnTree() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI node = new NonLeafNode("Is it small?",new LeafNode("mouse"),new LeafNode("elephant"));
        NodeI expectedAfterLearning = new NonLeafNode("Is it small?",new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")),
                new LeafNode("elephant"));

        List<String> aList = new ArrayList<String>();
        aList.add("Yes");

        // when
        NodeI nodeResulted = node.arrangeKnowledge(aList, "worm", "Does it have 1000 legs?", "Yes");

        // then
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testThreeLevelLearningOnTree() {
        // given
        writer = mock(OutStream.class);
        reader = mock(InStream.class);
        NodeI node = new NonLeafNode("Is it small?",new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")),
                new LeafNode("elephant"));
        NodeI expectedAfterLearning = new NonLeafNode("Is it small?",new NonLeafNode("Does it have 1000 legs?",new NonLeafNode("Is it microscopic?",new LeafNode("microb"),new LeafNode("worm")),new LeafNode("mouse")),
                new LeafNode("elephant"));
        List<String> aList = new ArrayList<String>();
        aList.add("Yes");
        aList.add("Yes");
        // when
        NodeI nodeResulted = node.arrangeKnowledge(aList, "microb", "Is it microscopic?", "Yes");

        // then
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }



    @Test
    public void testNodeEquality2() {
        NodeI root = new NonLeafNode("Is it big?",new LeafNode("elefant"),new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")));
        NodeI root2 = new NonLeafNode("Is it big?",new LeafNode("elefant"),new NonLeafNode("Does it have 1000 legs?",new LeafNode("worm"),new LeafNode("mouse")));
        Assert.assertEquals(root,root2);
    }


    private void stepNTimes(AnimalQuiz animalQuiz, int n) {
        for (int i=0;i<n;i++) {
            animalQuiz.step();
        }
    }

}



