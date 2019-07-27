package com.rete.core.action;

import java.util.Comparator;
import java.util.List;

/**
 * 条件接口
 */
public interface Condition {

    /**
     * 比较变量var1自比较
     * @param var1
     * @return
     */
    boolean isTrueFor(Object var1);

    /**
     * var1和var2比较
     * @param var1
     * @param var2
     * @return
     */
    boolean isTrueFor(Object var1,Object var2);

    /**
     * 是否是join 条件
     * @return
     */
    boolean isJoin();

    /**
     * 设置比较方式
     * @param comparator
     */
    void setComparator(Comparator comparator);

    /**
     * 获取事实集合
     * @return
     */
    List<Class> getFacts();

    /**
     * 设置规则code
     * @param ruleCode
     */
    void setRuleCode(String ruleCode);

    /**
     * 获取规则code
     */
    String getRuleCode();

    /**
     * 获取自身code
     */
    String getMyCode();

    /**
     * 获取参数集合
     * @return
     */
    List<String> getPropertyNames();

}
