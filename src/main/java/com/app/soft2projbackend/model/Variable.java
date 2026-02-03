package com.app.soft2projbackend.model;

import com.app.soft2projbackend.steprun.StepRun;

public class Variable {
    private String key;
    private Object value;

    public Variable() {}

    public Variable(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public Object getValue() { return value; }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}