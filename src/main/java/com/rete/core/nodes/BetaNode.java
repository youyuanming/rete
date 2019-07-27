package com.rete.core.nodes;

import com.rete.core.action.Condition;

import java.util.List;

public class BetaNode extends AbstractBetaNode {

    private Condition joinCondition = null;

    public BetaNode() {
    }

    public BetaNode(Condition c) {
        this.joinCondition = c;
    }

    public List<Class> getClasses() {
        return joinCondition.getFacts();
    }

    public boolean isTrueFor(Tuple tuple) {
        if(joinCondition != null){
            //如果连接条件不为空
            return joinCondition.isTrueFor(tuple.getObjects().get(0), tuple.getObjects().get(1));
        }
        return true;
    }

}
