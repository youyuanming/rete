package com.rete.core.nodes;

import com.rete.core.action.Condition;
import com.rete.core.action.Rule;

import java.util.*;

/**
 * 根节点
 */
public class RootNode {
    //所有ObjectTypeNode节点集合
    private Map<String,ObjectTypeNode> nodeMap = new HashMap<String,ObjectTypeNode>();

    /**
     * todo 添加规则
     */
    public void addRule(Rule rule){
        //装配规则相应的节点
        //获取当前规则有多少个facts的class
        Set<Class> factClazzs = rule.getFactsClass();
        //父节点
        Map<Class,Node> parentMap = new HashMap();
        //遗留的还需要处理的节点
        List<Node> remainingNodes = new ArrayList<Node>();
        //创建ObjectTypeNode
        for(Class clazz:factClazzs){
            ObjectTypeNode objectTypeNode = nodeMap.get(clazz.getName());
            if(objectTypeNode==null){
                objectTypeNode = new ObjectTypeNode(clazz);
                nodeMap.put(clazz.getName(),objectTypeNode);
            }
            parentMap.put(clazz,objectTypeNode);
        }
        //条件
        List<Condition> conditionList = rule.getObjectConditions();
        List temp = conditionList;
        Collections.sort(temp);//对conditionList排序
        //装配AlphaNode
        for(Condition condition:conditionList){
            //获取当前条件的class类型
            Class clazz = condition.getFacts().get(0);
            //获取当前class是否有父节点
            Node parentNode = parentMap.get(clazz);
            //创建AlphaNode
            Node alphaNode = new AlphaNode(condition);
            //如果当前父节点中不存在alphaNode则将alphaNode加入
            if(!parentNode.contains(alphaNode)){
                parentNode.addChildNode(alphaNode);
            }else {
                //如果存在相同的alphaNode节点,则不加入子节点
                alphaNode = parentNode.getChildNode(alphaNode);
            }
            parentNode = alphaNode;
            //当前alphaNode作为parentNode节点，开枝散叶
            parentMap.put(clazz,parentNode);
        }

        //创建Join条件
        List<Condition> joinConditionList = rule.getJoinConditions();
        //已使用的ObjectType对象
        List<Class> usedClasses = new ArrayList<Class>();
        for(Condition condition:joinConditionList){
            BetaNode betaNode = new BetaNode(condition);
            //ObjectType1
            Node left = parentMap.get(betaNode.getClasses().get(0));
            //ObjectType2
            Node right = parentMap.get(betaNode.getClasses().get(1));

            usedClasses.add(betaNode.getClasses().get(0));
            usedClasses.add(betaNode.getClasses().get(1));

            if (left != null && right != null) {
                //创建在ObjectType节点下
                betaNode.setLeftParent(left);
                betaNode.setRightParent(right);
                left.addChildNode(betaNode);
                right.addChildNode(betaNode);
            }
            //加入待处理节点
            remainingNodes.add(betaNode);
        }
        //异常ObjectTypeObject
        for (Class uc : usedClasses) {
            parentMap.remove(uc);
        }

        remainingNodes.addAll(parentMap.values());
        //将父节点凉凉相连
        //处理遗留节点大于1直到能找到TerminalNode节点
        while (remainingNodes.size()>1){
            //移除并获取左节点
            Node left = remainingNodes.remove(0);
            //移除并获取右节点
            Node right = remainingNodes.remove(0);
            //创建JoinNode
            BetaNode betaNode = new BetaNode();
            //设置左父节点
            betaNode.setLeftParent(left);
            //设置右父节点
            betaNode.setRightParent(right);
            //加入左父节点的子节点
            left.addChildNode(betaNode);
            //加入右父节点的子节点
            right.addChildNode(betaNode);
            //加入还需处理
            remainingNodes.add(betaNode);
        }
        // 一个规则只会有一个TerminalNode节点
        if (remainingNodes.size() == 1) {
            Node hNode = remainingNodes.get(0);
            TerminalNode tn = new TerminalNode(rule);
            hNode.addChildNode(tn);
        }
    }

    /**
     * 添加ObjectTypeNode
     */
    public void addObjectTypeNode(ObjectTypeNode objectTypeNode){
        this.nodeMap.put(objectTypeNode.getObjectType(),objectTypeNode);
    }

    /**
     * 获取ObjectTypeNode集合
     */
    public Map<String,ObjectTypeNode> getObjectTypeNodes(){return nodeMap;}

    /**
     * root节点执行objectTypeNode
     * @param tuple
     */
    public void sinkObject(Tuple tuple) {
        //循环执行其alphaNode
        for (ObjectTypeNode node : nodeMap.values()) {
            node.sinkObject(tuple);
        }
    }
}
