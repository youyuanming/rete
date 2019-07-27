package com.rete.core.nodes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ObjectTypeNode
 */
public class ObjectTypeNode implements Node {

    //对象类型->获取所传类从java语言规范定义的格式输出
    private String objectType;
    //对象class
    private Class objectClass;
    //子节点
    private Map<Node, Node> childNodes = new LinkedHashMap<Node, Node>();

    public ObjectTypeNode(Class objectClass){
        this.objectClass = objectClass;
        //获取所传类从java语言规范定义的格式输出
        this.objectType = objectClass.getCanonicalName();
    }

    public String getObjectType() {
        return objectType;
    }

    public Class getObjectClass() {
        return objectClass;
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
        return childNodes.get(key);
    }

    /**
     * 移除子节点
     *
     * @param node
     */
    public void removeChildNode(Node node) {
        childNodes.remove(node) ;
    }

    /**
     * 判断节点是否存在
     *
     * @param node
     * @return
     */
    public boolean contains(Node node) {
        return childNodes.get(node) != null;
    }



    /**
     * 下沉
     *
     * @param tuple
     */
    public void sinkObject(Tuple tuple) {
        //判断是否是自己的ObjectTypeNode
        if (!isTrueFor(tuple)) {
            return;
        }
        //遍历自己的子节点
        for (Node node : childNodes.values()) {
            //如果是继承了AbstractNode的中间节点
            if (node instanceof AbstractBetaNode) {
                AbstractBetaNode an = (AbstractBetaNode) node;
                if (an.isLeftParent(this)) {
                    an.sinkLeft(tuple);
                } else {
                    an.sinkRight(tuple);
                }
            } else {
                //实现了接口node
                node.sinkObject(tuple);
            }
        }
    }

    /**
     * 比较节点
     *
     * @param tuple
     * @return
     */
    public boolean isTrueFor(Tuple tuple) {
        return tuple.getObjects().get(0).getClass().getName().equals(objectType);
    }

    /**
     * 下沉
     *
     * @param node   自己节点
     * @param result 节点执行的结果
     */
    public void sinkObject(Node node, Boolean result) {

    }

    //重写hashCode，和equals 很重要，判断是不是同一个objectType
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
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
        ObjectTypeNode other = (ObjectTypeNode) obj;
        if (objectType == null) {
            if (other.objectType != null)
                return false;
        } else if (!objectType.equals(other.objectType))
            return false;
        return true;
    }
}
