package com.rete.core.action;

import sun.tools.jstat.Operator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JoinCondition implements Condition, Comparable {

    private Class factClass1;
    private Class factClass2;
    private String propertyName1;
    private String propertyName2;
    private String operator;
    private Comparator comparator;

    public JoinCondition(Class factClass1,String propertyName1,String operator,Class factClass2,String propertyName2){
        this.factClass1 = factClass1;
        this.factClass2 = factClass2;
        this.propertyName1 = propertyName1;
        this.propertyName2 = propertyName2;
        this.operator = operator;
    }

    /**
     * 比较变量var1自比较
     *
     * @param var1
     * @return
     */
    public boolean isTrueFor(Object var1) {
        return false;
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
            Method method1 = factClass1
                    .getDeclaredMethod("get" + propertyName1.substring(0, 1).toUpperCase() + propertyName1.substring(1), noparams);
            Object value1 = method1.invoke(var1, null);
            Method method2 = factClass2
                    .getDeclaredMethod("get" + propertyName2.substring(0, 1).toUpperCase() + propertyName2.substring(1), noparams);
            Object value2 = method2.invoke(var2, null);
            //比较两个值
            //todo value与propertyValue比较
            if(value1==null||value2==null){
                System.out.println("join参数为空");
                return false;
            }
            if(value1 instanceof String && value2 instanceof String){
                System.out.println("join执行比较：propertyName1:"+propertyName1+":"+value1+"==propertyName2:"+propertyName2+":"+value2);
                return value1.equals(value2);
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
        return true;
    }

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
        list.add(factClass1);
        list.add(factClass2);
        return list;
    }

    /**
     * 设置规则code
     *
     * @param ruleCode
     */
    public void setRuleCode(String ruleCode) {

    }

    /**
     * 获取规则code
     */
    public String getRuleCode() {
        return null;
    }

    /**
     * 获取自身code
     */
    public String getMyCode() {
        return null;
    }

    /**
     * 获取参数集合
     *
     * @return
     */
    public List<String> getPropertyNames() {
        return null;
    }


    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((factClass1 == null) ? 0 : factClass1.getName().hashCode());
        result = prime * result + ((factClass2 == null) ? 0 : factClass2.getName().hashCode());
        result = prime * result + ((operator == null) ? 0 : operator.hashCode());
        result = prime * result + ((propertyName1 == null) ? 0 : propertyName1.hashCode());
        result = prime * result + ((propertyName2 == null) ? 0 : propertyName2.hashCode());
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
        JoinCondition other = (JoinCondition) obj;
        if (factClass1 == null) {
            if (other.factClass1 != null)
                return false;
        } else if (factClass1 != other.factClass1)
            return false;
        if (factClass2 == null) {
            if (other.factClass2 != null)
                return false;
        } else if (factClass2 != other.factClass2)
            return false;
        if (operator == null) {
            if (other.operator != null)
                return false;
        } else if (!operator.equals(other.operator))
            return false;
        if (propertyName1 == null) {
            if (other.propertyName1 != null)
                return false;
        } else if (!propertyName1.equals(other.propertyName1))
            return false;
        if (propertyName2 == null) {
            if (other.propertyName2 != null)
                return false;
        } else if (!propertyName2.equals(other.propertyName2))
            return false;
        return true;
    }
}
