package com.rete.core.action;

import com.rete.core.nodes.Tuple;

/**
 * 执行结果
 */
public class Action {

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void execute(Tuple tuple) {
        task.execute(tuple);
    }

}
