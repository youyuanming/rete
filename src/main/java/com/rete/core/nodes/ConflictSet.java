package com.rete.core.nodes;


import java.util.*;

public class ConflictSet {

    private static Set<String> ruleCodes = new LinkedHashSet<String>();
    private static Map<String, Set<Tuple>> ruleTupleMap = new LinkedHashMap<String, Set<Tuple>>();
    private static Map<String, Integer> ruleIdTime = new LinkedHashMap<String, Integer>();

    private static int time = 1;

    public static void clear() {
        ruleCodes.clear();
        ruleTupleMap.clear();
    }

    public static int getTimeStamp(Integer ruleId) {
        return ruleIdTime.get(ruleId);
    }

    public static Collection<String> getActiveRuleIds() {

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>();
        entries.addAll(ruleIdTime.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            }
        });

        List<String> rules = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : entries) {

            if (ruleCodes.contains(entry.getKey())) {
                rules.add(entry.getKey());
            }
        }

        return rules;
    }

    public static Collection<Tuple> getTuplesByRuleId(Integer ruleId) {
        return ruleTupleMap.get(ruleId);
    }

    public static void print() {
        for (String ruleCode : ruleTupleMap.keySet()) {
            System.out.println("RuleId: " + ruleCode + " " + ruleTupleMap.get(ruleCode));
            System.out.println("");
        }
    }

    public static boolean contains(String ruleCode) {
        return ruleCodes.contains(ruleCode);
    }

    public static void addTuple(String ruleCode, Tuple tuple) {

        if (ruleIdTime.get(ruleCode) == null) {
            ruleIdTime.put(ruleCode, time++);
        }

        ruleCodes.add(ruleCode);
        Set<Tuple> list = ruleTupleMap.get(ruleCode);

        if (list == null) {
            ruleTupleMap.put(ruleCode, new HashSet());
        }

        ruleTupleMap.get(ruleCode).add(tuple);
    }

    public static void removeTuple(Integer ruleId, Tuple tuple) {

        if (ruleTupleMap.get(ruleId) != null) {
            ruleTupleMap.get(ruleId).remove(tuple);
        }

        if (ruleTupleMap.get(ruleId) == null || ruleTupleMap.get(ruleId).size() == 0) {
            ruleTupleMap.remove(ruleId);
            ruleCodes.remove(ruleId);
        }
    }
}
