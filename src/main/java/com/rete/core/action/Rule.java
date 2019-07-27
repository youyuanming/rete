package com.rete.core.action;

import com.rete.core.nodes.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Rule {
    private List<Condition> conditionRule = new ArrayList<Condition>();
    private List<Condition> joinConditionRule = new ArrayList<Condition>();
    private Set<Class> factClass = new HashSet<Class>();
    private String ruleCode;
    //执行的请求
    private List<Action> actions = new ArrayList<Action>();

    public Rule(){}

    public Rule(String ruleCode,List<Condition> conditions){
        this.ruleCode = ruleCode;
        for(Condition condition:conditions){
            if(condition instanceof JoinCondition){
                joinConditionRule.add(condition);
                factClass.add(condition.getFacts().get(0));
                factClass.add(condition.getFacts().get(1));
            }else {
                conditionRule.add(condition);
                factClass.add(condition.getFacts().get(0));
            }

        }
    }



    /**
     * 获取ruleCode
     * @return
     */
    public String getRuleCode(){
        return this.ruleCode;
    }

    /**
     * 获取当前facts有多少个class
     * @return
     */
    public Set<Class> getFactsClass() {
        return factClass;
    }

    /**
     * 获取有多少个条件
     */
    public List<Condition> getObjectConditions() {
        return conditionRule;
    }

    /**
     * 获取有多少个条件
     */
    public List<Condition> getJoinConditions() {
        return joinConditionRule;
    }


    public void addAction(Action action){
        actions.add(action);
    }

    public void fireEvent(Tuple tuple){
        for(Action action:actions){
            System.out.println("执行事件ruleCode="+ruleCode);
            action.execute(tuple);
        }
    }
}
