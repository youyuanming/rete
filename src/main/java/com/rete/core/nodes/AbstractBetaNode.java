package com.rete.core.nodes;

import java.util.List;

/**
 * 抽象节点
 */
public abstract class AbstractBetaNode implements Node {
    //左父节点
    protected Node leftParent = null;
    //右父节点
    protected Node rightParent = null;
    //子节点
    protected Node childNode = null;
    //beta缓存
    protected BetaMemory memory = new BetaMemory();

    public void sinkObject(Tuple tuple) {
        sinkLeft(tuple);
    }

    public boolean contains(Node node) {
        return this.childNode == node;
    }

    public Node getChildNode(Object key) {
        return this.childNode;
    }

    public void addChildNode(Node node) {
        this.childNode = node;
    }

    public void removeChildNode(Node node) {
        //todo
    }

    public boolean checkConstraints(Tuple leftTuple, Tuple rightTuple) {
        return true;
    }

    /**
     * 是否是左父节点
     * @param p
     * @return
     */
    public boolean isLeftParent(Node p) {
        if (leftParent == p) {
            return true;
        }
        return false;
    }

    /**
     * 是否是右父节点
     * @param p
     * @return
     */
    public boolean isRightParent(Node p) {
        if (rightParent == p) {
            return true;
        }
        return false;
    }

    public void sinkLeft(Tuple tuple) {
        //加入beta节点左节点执行缓存
        memory.addLeftTuple(tuple);
        //获取beta右节点缓存
        List<Tuple> rightList = memory.getRightTupleMemory();
        System.out.println("left class:"+tuple.getObjects());
        //遍历右节点缓存看看是否有匹配的结果
        for (Tuple t : rightList) {
            System.out.println("left class2:"+t.getObjects());
            //如果是betaNode，默认是true
            if (!checkConstraints(tuple, t)) {
                return;
            }
            //merge一个Tuple，索引0是左节点fact，1是右节点fact
            Tuple merged = tuple.mergeTuple(t);
            //比较左节点和右节点的值
            boolean pkResult = isTrueFor(merged);
            if (pkResult) {
                if (childNode != null) {
                    if (childNode instanceof AbstractBetaNode) {
                        AbstractBetaNode cn = (AbstractBetaNode) childNode;
                        if (cn.isLeftParent(this)) {
                            cn.sinkLeft(merged);
                        } else {
                            cn.sinkRight(merged);
                        }
                    } else {
                        //如果下游是TerminalNode
                        childNode.sinkObject(merged);
                    }
                }
            }
        }
    }

    public void sinkRight(Tuple tuple) {
        memory.addRightTuple(tuple);
        List<Tuple> leftList = memory.getLeftTupleMemory();
        System.out.println("right class:"+tuple.getObjects());
        for (Tuple t : leftList) {
            System.out.println("right class2:"+t.getObjects());
            if (!checkConstraints(t, tuple)) {
                return;
            }
            Tuple merged = t.mergeTuple(tuple);
            boolean pkResult = isTrueFor(merged);
            if (pkResult) {
                if (childNode != null) {
                    if (childNode instanceof AbstractBetaNode) {
                        AbstractBetaNode cn = (AbstractBetaNode) childNode;
                        if (cn.isLeftParent(this)) {
                            cn.sinkLeft(merged);
                        } else {
                            cn.sinkRight(merged);
                        }
                    } else {
                        childNode.sinkObject(merged);
                    }
                }
            }
        }
    }

    public Node getLeftParent() {
        return leftParent;
    }

    public void setLeftParent(Node leftParent) {
        this.leftParent = leftParent;
    }

    public Node getRightParent() {
        return rightParent;
    }

    public void setRightParent(Node rightParent) {
        this.rightParent = rightParent;
    }

    public Node getChildNode() {
        return childNode;
    }

    public void setChildNode(Node childNode) {
        this.childNode = childNode;
    }


}
