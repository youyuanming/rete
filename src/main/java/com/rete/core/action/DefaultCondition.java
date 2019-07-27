package com.rete.core.action;

import sun.tools.jstat.Operator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 默认条件
 */
public class DefaultCondition implements Condition, Comparable {
    //事实的class
    private Class factClass;
    //参数名
    private String propertyName;
    //参数value
    private Object propertyValue;
    //比较符
    private String operator;
    //自定义比较方式
    private Comparator comparator;
    //对象名称
    private String className = null;
    //自身的code
    private String code;
    //规则code
    private String ruleCode;

    public DefaultCondition(Class factClass, String propertyName, String operator, Object propertyValue) {
        this.factClass = factClass;
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.operator = operator;
        this.code = "RULE_CONDITION_CODE_1"+propertyName;
        className = this.factClass.getCanonicalName();
    }

    /**
     * 比较变量var1自比较
     *
     * @param var1
     * @return
     */
    public boolean isTrueFor(Object var1) {
        return isTrueFor(var1,null);
    }

    /**
     * var1和var2比较
     *
     * @param var1
     * @param var2
     * @return
     */
    public boolean isTrueFor(Object var1, Object var2) {
        Class noparams[] = {};
        try {
            //通过反射获取fact中的参数值
            Method method = factClass
                    .getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), noparams);
            Object value = method.invoke(var1, null);
            //比较两个值
            //todo value与propertyValue比较
             if(value==null){
                System.out.println("参数为空");
                return false;
            }
            if(propertyValue instanceof String){
                System.out.println("执行比较："+value+"=="+propertyValue);
                return propertyValue.equals(value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是join 条件
     *
     * @return
     */
    public boolean isJoin() {
        return false;
    }

    /**
     * 设置比较方式
     *
     * @param comparator
     */
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    /**
     * 获取事实集合
     *
     * @return
     */
    public List<Class> getFacts() {
        List<Class> list = new ArrayList<Class>();
        list.add(factClass);
        return list;
    }

    /**
     * 设置规则code
     *
     * @param ruleCode
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * 获取规则code
     */
    public String getRuleCode() {
        return this.ruleCode;
    }

    /**
     * 获取自身code
     */
    public String getMyCode() {
        return this.code;
    }

    /**
     * 获取参数集合
     *
     * @return
     */
    public List<String> getPropertyNames() {
        List<String> list = new ArrayList<String>();
        list.add(propertyName);
        return list;
    }

    /**
     * 比较两个条件是不是一样的
     * @param o
     * @return
     */
    public int compareTo(Object o) {
        return 0;
    }


}
