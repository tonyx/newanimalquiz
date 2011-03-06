package test.org.tonyxzt.kata;

import org.junit.Ignore;
import org.junit.Test;
import org.tonyzt.kata.AnimalQuiz;
import org.tonyzt.kata.InStream;
import org.tonyzt.kata.Node;
import org.tonyzt.kata.OutStream;

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
    public void shouldAskToThinkOfAnAnimal() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, "elefant");
        animalQuiz.start();
        verify(writer).output("think of an animal");
    }

    @Test
    public void when_starting_state_is_elefant_then_ask_if_it_was_an_elefant() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);
        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, "elefant");
        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();
        verify(writer).output("Is it a elefant?");
    }


    @Test
    public void guess_elefant_ask_if_it_was_it_then_the_answer_is_no_then_ask_what_it_was() {
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
    public void when_knowelege_base_is_elefant_and_mouse_first_question_is_the_discriminating_question() {
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

        when(inputData.getInput()).thenReturn("No"); //.thenReturn("No").thenReturn("Yes");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();

        verify(writer).output("Is it a big animal?");
        animalQuiz.step();

        verify(writer).output("Is it a mouse?");
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
    public void when_knowelege_base_is_elefant_and_domain_objects_mouse_first_question_is_the_discriminating_question_then_guess_elefant() {
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

        verify(writer).output("What question would you suggest to distinguish a worm from a mouse?");
        animalQuiz.step();

    }


    @Test
    @Ignore
    public void after_learnt_a_mouse_can_guess_if_was_elefant_or_mouse() {
        OutStream writer = mock(OutStream.class);
        InStream inputData = mock(InStream.class);

        AnimalQuiz animalQuiz = new AnimalQuiz(inputData, writer, "elefant");
        when(inputData.getInput()).thenReturn("No").thenReturn("mouse").thenReturn("Is it a big animal?").thenReturn("No");

        animalQuiz.start();
        verify(writer).output("think of an animal");
        animalQuiz.step();
        verify(writer).output("was it a elefant?");
        animalQuiz.step();
        verify(writer).output("what animal was it?");
        animalQuiz.step();
        verify(writer).output("what question will you suggest to distinguish a elefant from a mouse?");
        animalQuiz.step();
        verify(writer).output("and what the answer would be?");
        animalQuiz.step();

        verify(writer).output("think of an animal");

    }


}



