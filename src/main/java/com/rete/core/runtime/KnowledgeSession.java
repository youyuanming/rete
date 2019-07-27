package com.rete.core.runtime;

import com.rete.core.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeSession {

    private Map<Node,Boolean> betaMemory = new HashMap<Node,Boolean>();
    private Map<Node,Boolean> alphaMemory = new HashMap<Node,Boolean>();

    public void getBetaMemory(){
        return;
    }

    public void getAlphaMemory(){
        return;
    }
}
