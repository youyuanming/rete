package com.rete.core.nodes;

import java.util.Map;

public class JoinNode extends AbstractBetaNode {
    /**
     * 比较节点集合
     *
     * @param tuple
     * @return
     */
    public boolean isTrueFor(Tuple tuple) {
        return true;
    }

    /**
     * 下沉
     *
     * @param node   自己节点
     * @param result 节点执行的结果
     */
    public void sinkObject(Node node, Boolean result) {

    }

    /**
     * If parents of a join node are both beta node and they have common type of
     * object then those objects must match.
     *
     * This is essential for double join between three Classes.
     *
     */
    public boolean checkConstraints(Tuple leftTuple, Tuple rightTuple) {

        //	if ((leftParent instanceof BetaNode) && rightParent instanceof BetaNode) {

        Map<Class, Object> leftMap = leftTuple.getClassObjectsMap();
        Map<Class, Object> rightMap = rightTuple.getClassObjectsMap();

        for (Class cl : leftMap.keySet()) {

            if (rightMap.get(cl) != null) {
                if (!leftMap.get(cl).equals(rightMap.get(cl))) {
                    return false;
                }
            }
        }
        //	}
        return true;
    }
}
