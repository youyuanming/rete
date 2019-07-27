package com.rete.core.nodes;

/**
 * 节点属性
 */
public interface Node {

    /**
     * 添加子节点
     */
    void addChildNode(Node node);
    /**
     * 获取子节点
     */
    Node getChildNode(Object key);

    /**
     * 移除子节点
     */
    void removeChildNode(Node node);

    /**
     * 判断节点是否存在
     * @param node
     * @return
     */
    boolean contains(Node node);

    /**
     * 下沉
     * @param tuple
     */
    void sinkObject(Tuple tuple);

    /**
     * 比较节点集合
     * @param tuple
     * @return
     */
    boolean isTrueFor(Tuple tuple);

}
