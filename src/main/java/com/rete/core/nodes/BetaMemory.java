package com.rete.core.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * beta结果缓存
 */
public class BetaMemory {
    //左集合缓存
    private List<Tuple> leftTupleMemory = new ArrayList<Tuple>();
    //右集合缓存
    private List<Tuple> rightTupleMemory = new ArrayList<Tuple>();

    public BetaMemory() {
    }

    public void removeTuple(Tuple tuple) {
        leftTupleMemory.remove(tuple);
        rightTupleMemory.remove(tuple);
    }

    public void addLeftTuple(Tuple tuple) {
        leftTupleMemory.add(tuple);
    }

    public void addRightTuple(Tuple tuple) {
        rightTupleMemory.add(tuple);
    }

    public List<Tuple> getLeftTupleMemory() {
        return leftTupleMemory;
    }

    public List<Tuple> getRightTupleMemory() {
        return rightTupleMemory;
    }


}
