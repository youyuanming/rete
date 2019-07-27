package com.rete.core.nodes;

import com.rete.core.action.Rule;
import com.rete.core.exception.ReteException;

/**
 * 终节点
 */
public class TerminalNode implements Node {

    private Tuple tuple = null;
    private String ruleCode = null;
    private Rule rule = null;
    private boolean isActive = false;

    public TerminalNode(Rule rule){
        this.rule = rule;
        this.ruleCode = rule.getRuleCode();
    }

    /**
     * 添加子节点
     *
     * @param node
     */
    public void addChildNode(Node node) {
        throw new ReteException("不存在子节点");
    }

    /**
     * 获取子节点
     *
     * @param key
     */
    public Node getChildNode(Object key) {
        throw new ReteException("不存在子节点");
    }

    /**
     * 移除子节点
     *
     * @param node
     */
    public void removeChildNode(Node node) {
        throw new ReteException("不存在子节点");
    }

    /**
     * 判断节点是否存在
     *
     * @param node
     * @return
     */
    public boolean contains(Node node) {
        throw new ReteException("不存在子节点");
    }

    /**
     * 下沉
     *
     * @param tuple
     */
    public void sinkObject(Tuple tuple) {
        System.out.println("TerminalNode:"+ruleCode);
        this.tuple = tuple;
        isActive = true;
        //执行事件
        rule.fireEvent(tuple);
    }

    /**
     * 比较节点集合
     *
     * @param tuple
     * @return
     */
    public boolean isTrueFor(Tuple tuple) {
        throw new ReteException("不存在子节点");
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
