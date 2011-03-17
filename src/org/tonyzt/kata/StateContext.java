package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 13/03/11
 * Time: 20.45
 * To change this template use File | Settings | File Templates.
 */
public class StateContext {
    private State state;
    public StateContext() {
        setState(new Started());

    }
    public void setState(State newState) {
        this.state = newState;
    }

    public void step(InStream inStream, OutStream outStream) {
        this.state.step(this,inStream,outStream);
    }

}
