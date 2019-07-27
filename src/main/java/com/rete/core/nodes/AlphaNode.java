package com.rete.core.nodes;


import com.rete.core.action.Condition;

import java.util.HashMap;
import java.util.Map;

/**
 * AlphaNode
 */
public class AlphaNode implements Node {
    //条件
    private Condition condition;
    //子节点
    private Map<Node, Node> childNodes = new HashMap<Node, Node>();
    //子节点
    private Map<String, Boolean> alphaNodeMemory = new HashMap<String, Boolean>();


    public AlphaNode(Condition condition){
        this.condition = condition;
    }

    public String getClassName() {
        return condition.getFacts().get(0).getCanonicalName();
    }

    /**
     * 添加子节点
     *
     * @param node
     */
    public void addChildNode(Node node) {
        this.childNodes.put(node,node);
    }

    /**
     * 获取子节点
     *
     * @param key
     */
    public Node getChildNode(Object key) {
        return this.childNodes.get(key);
    }

    /**
     * 移除子节点
     *
     * @param node
     */
    public void removeChildNode(Node node) {
        this.childNodes.remove(node);
    }

    /**
     * 判断节点是否存在
     *
     * @param node
     * @return
     */
    public boolean contains(Node node) {
        return this.childNodes.get(node)!=null;
    }

    /**
     * 执行
     *
     * @param tuple
     */
    public void sinkObject(Tuple tuple) {
        //todo 获取当前alphaNode是否有执行过的结果
        //判断当前alphaNode的condition是否满足如果满足走后续节点,并缓存alphaNode执行的结果
        Boolean alphaExc = isTrueFor(tuple);
        //todo 缓存当前alphaNode执行的结果
        System.out.println("alphaNode执行结果为："+alphaExc);
        if(!alphaExc){
            return;
        }
        //遍历执行子节点
        for(Node node:childNodes.values()){
            //遍历继承AbstractNode的中间节点
            if(node instanceof AbstractBetaNode){
                AbstractBetaNode an = (AbstractBetaNode) node;
                if(an.isLeftParent(this)){
                    an.sinkLeft(tuple);
                }else {
                    an.sinkRight(tuple);
                }
            }else {
                //遍历子节点
                node.sinkObject(tuple);
            }
        }
    }

    /**
     * 比较节点集合
     *
     * @param tuple
     * @return
     */
    public boolean isTrueFor(Tuple tuple) {
        return condition.isTrueFor(tuple.getObjects().get(0));
    }

    /**
     * 下沉
     *
     * @param node   自己节点
     * @param result 节点执行的结果
     */
    public void sinkObject(Node node, Boolean result) {

    }

    //很重要，重写hashCode和equals判断节点的condition是不是一样
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlphaNode other = (AlphaNode) obj;
        if (condition == null) {
            if (other.condition != null)
                return false;
        } else if (!condition.equals(other.condition))
            return false;
        return true;
    }
}
