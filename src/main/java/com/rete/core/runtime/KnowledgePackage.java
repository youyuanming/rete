package com.rete.core.runtime;

import com.rete.core.action.*;
import com.rete.core.facts.Student;
import com.rete.core.facts.Subject;
import com.rete.core.facts.Users;
import com.rete.core.nodes.RootNode;
import com.rete.core.nodes.Tuple;

import java.util.*;

public class KnowledgePackage {

    private List<Rule> rules = new ArrayList<Rule>();
    private RootNode root = null;
    private Map<String, Rule> ruleMap = new LinkedHashMap();
    private Map<String, Set<String>> rulePatternMap = new LinkedHashMap<String, Set<String>>();
    private Set<Object> objectMemory = new LinkedHashSet<Object>();

    public static void main(String[] args) {
        Condition condition = new DefaultCondition(Student.class,"name","==","张三");
        Condition condition2 = new DefaultCondition(Subject.class,"subjectName","==","班级");
        Condition condition3 = new DefaultCondition(Users.class,"name","==","张三");
        Condition condition4 = new JoinCondition(Student.class,"name","==",Users.class,"name");
        Task task = new Task(){
            public void execute(Tuple tuple) {
                System.out.println("执行task任务"+tuple);
            }
        };
        Action action = new Action();
        action.setTask(task);
        Action action2 = new Action();
        action2.setTask(task);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(condition);
        conditions.add(condition2);
        conditions.add(condition3);
        conditions.add(condition4);
        Rule rule = new Rule("RULE_CODE_1",conditions);
        rule.addAction(action);
        List<Condition> conditions2 = new ArrayList<Condition>();
        conditions2.add(condition);
        conditions2.add(condition2);
        conditions2.add(condition3);
        Rule rule2 = new Rule("RULE_CODE_2",conditions2);
        rule2.addAction(action2);
        KnowledgePackage knowledgePackage = new KnowledgePackage();
        knowledgePackage.addRule(rule);
        knowledgePackage.addRule(rule2);
        Student student = new Student();
        student.setName("张三");
        knowledgePackage.insert(student);
        Student student2 = new Student();
        student2.setName("张三");
        knowledgePackage.insert(student2);
        Subject subject = new Subject();
        subject.setSubjectName("班级");
        knowledgePackage.insert(subject);
        Users users = new Users();
        users.setName("张三");
        knowledgePackage.insert(users);
        knowledgePackage.builder();
        knowledgePackage.fireAllRules();
    }

    /**
     * 获取构建知识包
     * @return
     */
    public void builder(){
        RootNode root = new RootNode();
        Map<String, Rule> ruleMap = new LinkedHashMap<String, Rule>();

        for (Rule r : rules) {
            ruleMap.put(r.getRuleCode(), r);
            root.addRule(r);
        }
        this.root = root;
    }

    /**
     * 执行规则
     * @return
     */
    public void fireAllRules(){
        for(Object o:objectMemory){
            //执行规则网络
            root.sinkObject(new Tuple(o));
            //获取TerminalNode节点
        }
        //递归重新执行规则网络
    }

    /**
     * 添加规则
     * @param rule
     */
    public void addRule(Rule rule){
        rules.add(rule);
        ruleMap.put(rule.getRuleCode(),rule);
    }

    /**
     * 插入事实
     * @param fact
     */
    public void insert(Object fact){
        objectMemory.add(fact);
    }
}
