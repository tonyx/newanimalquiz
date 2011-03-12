package test.org.tonyxzt.kata;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.Node;
import org.tonyzt.kata.OutStream;

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
    @Test
    public void when_starting_state_is_elefant_then_first_question_is_if_it_is_elefant() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, "elefant");
        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();
        verify(writer).output("Is it a elefant?");
    }


    @Test
    public void when_thinking_about_a_mouse_then_should_ask_how_to_distinguish_mouse_from_elephant() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node node = mock(Node.class);
        when(node.getAnimal()).thenReturn("elefant");
        when(node.isLeaf()).thenReturn(true);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, node);

        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it a big animal").thenReturn("No");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a elefant?");
        animalQuiz.step();

        verify(writer).output("What animal was?");
        animalQuiz.step();
        verify(writer).output("What question would you suggest to distinguish a elefant from a mouse?");
        animalQuiz.step();
        verify(writer).output("What should be the answer to the question \"Is it a big animal\" to indicate a mouse compared to a elefant?");
    }

    @Test
    @Ignore
    public void answers_different_from_no_and_yes_should_not_be_allowed() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node node = mock(Node.class);
        when(node.getAnimal()).thenReturn("elefant");
        when(node.isLeaf()).thenReturn(true);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, node);

        when(inputData.getInput()).thenReturn("Ni").thenReturn("No");//.thenReturn("mouse").thenReturn("Is it a big animal").thenReturn("No");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

//        verify(writer).output("Is it a elefant?");
//        animalQuiz.step();

        animalQuiz.step();
        animalQuiz.step();
        verify(writer,times(2)).output("Is it a elefant?");

//        verify(writer).output("What animal was?");
//        animalQuiz.step();
//
//        verify(writer).output("What question would you suggest to distinguish a elefant from a mouse?");
//        animalQuiz.step();
//
//        verify(writer).output("What should be the answer to the question \"Is it a big animal\" to indicate a mouse compared to a elefant?");
    }

    @Test
    public void when_knowelege_base_is_elefant_and_mouse_first_question_is_the_discriminating_question_then_guess_elefant() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node elefant = mock(Node.class);
        when(elefant.getAnimal()).thenReturn("elefant");
        when(elefant.isLeaf()).thenReturn(true);

        Node mouse = mock(Node.class);
        when(mouse.getAnimal()).thenReturn("mouse");
        when(mouse.isLeaf()).thenReturn(true);

        Node root = mock(Node.class);
        when(root.isLeaf()).thenReturn(false);
        when(root.getQuestion()).thenReturn("Is it a big animal?");
        when(root.getYesBranch()).thenReturn(elefant);
        when(root.getNoBranch()).thenReturn(mouse);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("Yes"); //.thenReturn("No").thenReturn("Yes");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a elefant?");
    }


    @Test
    public void when_base_is_elefant_and_domain_objects_mouse_first_question_is_the_discriminating_question_then_guess_elefant() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node elefant = new Node("elefant");
        elefant.setLeaf(true);
        Node mouse = new Node("mouse");
        mouse.setLeaf(true);
        Node root = new Node();
        root.setLeaf(false);
        root.setQuestion("Is it a big animal?");
        root.setNoBranch(mouse);
        root.setYesBranch(elefant);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("Yes");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a elefant?");
    }

    @Test
    public void testNewConstructor() {
        OutStream ouputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node root = new Node("Is it big?",new Node("elefant"),new Node("mouse"));
        when(inputData.getInput()).thenReturn("No");
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,ouputData,root);
        animalQuiz.start();
        verify(ouputData).output("think of an animal");
        animalQuiz.step();
        verify(ouputData).output("Is it big?");
        animalQuiz.step();
        verify(ouputData).output("Is it a mouse?");
    }

    @Test
    public void testOneLevelLearning() {
        OutStream ouputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node root = new Node("elephant");
        Node expectedAfterLearning = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,ouputData,root);
        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it big?").thenReturn("No");
        animalQuiz.start();
        verify(ouputData).output("think of an animal");
        animalQuiz.step();
        verify(ouputData).output("Is it a elephant?");
        animalQuiz.step();
        verify(ouputData).output("What animal was?");
        animalQuiz.step();
        verify(ouputData).output("What question would you suggest to distinguish a elephant from a mouse?");
        animalQuiz.step();
        verify(ouputData).output("What should be the answer to the question \"Is it big?\" to indicate a mouse compared to a elephant?");
        animalQuiz.step();

        Assert.assertEquals(expectedAfterLearning, animalQuiz.getNode());
    }

    @Test
    public void testTwoLevelLearning() {
        OutStream ouputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node startNode = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        Node expected = new Node("Is it big?",new Node("elephant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,ouputData,startNode);
        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        animalQuiz.start();
        verify(ouputData).output("think of an animal");
        animalQuiz.step();
        verify(ouputData).output("Is it big?");
        animalQuiz.step();
        verify(ouputData).output("Is it a mouse?");
        animalQuiz.step();
        verify(ouputData).output("What animal was?");
        animalQuiz.step();
        verify(ouputData).output("What question would you suggest to distinguish a mouse from a worm?");
        animalQuiz.step();
        verify(ouputData).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");
        animalQuiz.step();
        Assert.assertEquals(expected, animalQuiz.getNode());
    }


    @Test
    public void testThreeLevelLearning() {
        OutStream ouputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node startNode = new Node("Is it big?",new Node("elephant"),new Node("mouse"));
        Node expected = new Node("Is it big?",new Node("elephant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,ouputData,startNode);
        when(inputData.getInput()).thenReturn("No").thenReturn("No").thenReturn("worm").thenReturn("Does it have 1000 legs?").thenReturn("Yes");
        animalQuiz.start();
        verify(ouputData).output("think of an animal");
        animalQuiz.step();
        verify(ouputData).output("Is it big?");
        animalQuiz.step();
        verify(ouputData).output("Is it a mouse?");
        animalQuiz.step();
        verify(ouputData).output("What animal was?");
        animalQuiz.step();
        verify(ouputData).output("What question would you suggest to distinguish a mouse from a worm?");
        animalQuiz.step();
        verify(ouputData).output("What should be the answer to the question \"Does it have 1000 legs?\" to indicate a worm compared to a mouse?");
        animalQuiz.step();
        Assert.assertEquals(expected, animalQuiz.getNode());
        when(inputData.getInput()).thenReturn("No").thenReturn("Yes").thenReturn("No").thenReturn("microb").thenReturn("Is it microscopic?").thenReturn("Yes");
        verify(ouputData).output("think of an animal");
        animalQuiz.step();
        //verify(ouputData).output("Is it big?");
        animalQuiz.step();
        verify(ouputData).output("Does it have 1000 legs?");
        animalQuiz.step();
        verify(ouputData).output("Is it a worm?");
        animalQuiz.step();
        verify(ouputData,times(2)).output("What animal was?");
        animalQuiz.step();
        verify(ouputData).output("What question would you suggest to distinguish a worm from a microb?");
        animalQuiz.step();
        verify(ouputData).output("What should be the answer to the question \"Is it microscopic?\" to indicate a microb compared to a worm?");
        animalQuiz.step();
    }





    @Test
    @Ignore
    public void spike() {
        OutStream outputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node root = new Node("animal");
    //    root = root.arrangeByPath()
        Node noBranch = new Node("answer no");
        Node yesBranch = new Node("answer yes");
        root.arrangeByPath(Arrays.asList("Yes"),"animal yes 1","is it yes 1?","Yes");

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,outputData,root);
        when(inputData.getInput()).thenReturn("yes");
        animalQuiz.start();
        verify(outputData).output("think of an animal");
        animalQuiz.step();
        verify(outputData).output("Is it 0?");
        animalQuiz.step();
        verify(outputData).output("Is it animal yes 1?");




//        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,outputData, new Node("elephant"));


//        when(inputData.getInput()).thenReturn("No").
//                thenReturn("mouse").thenReturn("Is it small?").thenReturn("Yes").
//                thenReturn("Yes").thenReturn("No").
//                thenReturn("worm").thenReturn("Does it have 1000 foots?").thenReturn("Yes").
//                thenReturn("Yes").thenReturn("No").thenReturn("No").
//                thenReturn("microb").thenReturn("Is it microscopic?");
//        ;
//
//        animalQuiz.start();
//        verify(outputData).output("think of an animal");
//        animalQuiz.step();
//        verify(outputData).output("Is it a elephant?");
//        animalQuiz.step();
//        //verify(outputData).output("What animal was?");
//        animalQuiz.step();
//        verify(outputData).output("What question would you suggest to distinguish a elephant from a mouse?");
//        animalQuiz.step();
//        verify(outputData).output("What should be the answer to the question \"Is it small?\" to indicate a mouse compared to a elephant?");
//        animalQuiz.step();
//        verify(outputData).output("Is it small?");
//        animalQuiz.step();
//        verify(outputData).output("Is it a mouse?");
//        animalQuiz.step();
//        verify(outputData,times(2)).output("What animal was?");
//        animalQuiz.step();
//        verify(outputData).output("What question would you suggest to distinguish a mouse from a worm?");
//        animalQuiz.step();
//        verify(outputData).output("What should be the answer to the question \"Does it have 1000 foots?\" to indicate a worm compared to a mouse?");
//        animalQuiz.step();
//        verify(outputData,times(2)).output("Is it small?");
//        animalQuiz.step();
//        verify(outputData,times(1)).output("Does it have 1000 foots?");
//        animalQuiz.step();
//        verify(outputData).output("What should be the answer to the question \"Is it microscopic?\" to indicate a microb compared to a worm?");
//        animalQuiz.step();


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
        Node nodeResulted = node.arrangeByPath(aList,"microb","Is it microscopic?","Yes");
        Assert.assertEquals(expectedAfterLearning,nodeResulted);
    }


    @Test
    public void testNewConstructor2() {
        OutStream ouputData = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        Node root = new Node("Is it big?",new Node("elefant"),new Node("Does it have 1000 legs?",new Node("worm"),new Node("mouse")));
        when(inputData.getInput()).thenReturn("No","Yes");

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData,ouputData,root);

        animalQuiz.start();
        verify(ouputData).output("think of an animal");
        animalQuiz.step();

        verify(ouputData).output("Is it big?");
        animalQuiz.step();

        verify(ouputData).output("Does it have 1000 legs?");
        animalQuiz.step();

        verify(ouputData).output("Is it a worm?");
        animalQuiz.step();
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


    @Test
    @Ignore
    public void deeperLevel_knowelege_tree() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node elefant = new Node("elefant");
        elefant.setLeaf(true);
        Node mouse = new Node("mouse");
        mouse.setLeaf(true);
        Node bird = new Node("bird");
        mouse.setLeaf(true);
        Node root = new Node();
        root.setLeaf(false);
        Node subRoot = new Node();
        subRoot.setLeaf(false);
        subRoot.setQuestion("Does it fly?");
        subRoot.setNoBranch(mouse);
        subRoot.setYesBranch(bird);
        root.setQuestion("Is it a big animal?");
        root.setYesBranch(elefant);
        root.setNoBranch(subRoot);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("No");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Does it fly?");
    }







    @Test
    public void deeper_deeper_knowelege_base() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node elefant = new Node("elefant");
        elefant.setLeaf(true);

        Node mouse = new Node("mouse");
        mouse.setLeaf(true);

        Node root = new Node();
        root.setQuestion("Is it a big animal?");
        root.setLeaf(false);

        Node worm = new Node("worm");

        Node insectOrNot = new Node();
        insectOrNot.setQuestion("Is it an insect?");
        insectOrNot.setLeaf(false);

        insectOrNot.setYesBranch(worm);
        insectOrNot.setNoBranch(mouse);

        Node doesItFly = new Node();
        Node bird = new Node("bird");
        bird.setLeaf(true);

        doesItFly.setLeaf(false);
        doesItFly.setQuestion("Does it fly?");
        doesItFly.setNoBranch(insectOrNot);
        doesItFly.setYesBranch(bird);

        root.setYesBranch(elefant);
        root.setNoBranch(doesItFly);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("No").thenReturn("No");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();
        verify(writer).output("Is it a big animal?");
        animalQuiz.step();
        verify(writer).output("Does it fly?");
        animalQuiz.step();
        verify(writer).output("Is it an insect?");
        animalQuiz.step();
    }


    @Test
    public void learn_distinguish_elefant_from_mouse() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node root = new Node("elefant");
        root.setLeaf(true);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it a big animal?").thenReturn("no").thenReturn("no").thenReturn("worm").thenReturn("is it an insect?");//.thenReturn("yes");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a elefant?");
        animalQuiz.step();

        verify(writer).output("What animal was?"); // mouse
        animalQuiz.step();

        verify(writer).output("What question would you suggest to distinguish a elefant from a mouse?");
        animalQuiz.step();

        verify(writer).output("What should be the answer to the question \"Is it a big animal?\" to indicate a mouse compared to a elefant?");
        animalQuiz.start();

        verify(writer,times(2)).output("think of an animal");
        animalQuiz.step();

        verify(writer,times(1)).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a mouse?");
        animalQuiz.start();

        verify(writer,times(3)).output("think of an animal");
        animalQuiz.step();

        verify(writer,times(2)).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a mouse?");
        animalQuiz.step();

        verify(writer).output("What animal was?");
        animalQuiz.step();

//        verify(writer).output("What question would you suggest to distinguish a worm from a mouse?");
//        animalQuiz.step();
    }

    @Test
    @Ignore
    public void knowelege_base_deep() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        Node root = new Node("elefant");
        root.setLeaf(true);


        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, root);
        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it a big animal?").thenReturn("no").thenReturn("no").thenReturn("worm").thenReturn("is it an insect?");//.thenReturn("yes");


        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a elefant?");
        animalQuiz.step();

        verify(writer).output("What animal was?"); // mouse
        animalQuiz.step();

        verify(writer).output("What question would you suggest to distinguish a elefant from a mouse?");
        animalQuiz.step();

        verify(writer).output("What should be the answer to the question \"Is it a big animal?\" to indicate a mouse compared to a elefant?");
        animalQuiz.start();

        verify(writer,times(2)).output("think of an animal");
        animalQuiz.step();

        verify(writer,times(2)).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a mouse?");
        animalQuiz.start();


        verify(writer,times(3)).output("think of an animal");
        animalQuiz.step();

        verify(writer,times(3)).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a mouse?");
        animalQuiz.step();

        verify(writer).output("What animal was?");
        animalQuiz.step();

//        verify(writer).output("What question would you suggest to distinguish a worm from a mouse?");
//        animalQuiz.step();
    }








}



