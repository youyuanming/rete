package com.rete.core.facts;

public class Base {

    private int reteParamVersion=0;

    public int getReteParamVersion() {
        return reteParamVersion;
    }

    public void setReteParamVersion(int reteParamVersion) {
        this.reteParamVersion = reteParamVersion;
    }

    public void addVersion(){
        this.reteParamVersion++;
    }

}
