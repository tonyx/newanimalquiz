package org.tonyzt.kata;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 13/03/11
 * Time: 20.41
 * To change this template use File | Settings | File Templates.
 */
public interface State {
    public void step(StateContext stateContext,InStream inStreamm, OutStream outStream);
}
