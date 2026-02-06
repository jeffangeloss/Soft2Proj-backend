package com.app.soft2projbackend.model;

import java.util.ArrayList;
import java.util.List;

public class ExecutionContext {
    private Flow flow;
    private List<Variable> variables = new ArrayList<>();

    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    public Flow getFlow() {
        return flow;
    }
    public List<Variable> getVariableList() {
        return variables;
    }

    public void put(String key, Object value) {
        variables.stream()
                .filter(v -> v.getKey().equals(key))
                .findFirst()
                .ifPresentOrElse(v -> {
                            v.setValue(value);
                        },
                        () -> variables.add(new Variable(key, value))
                );
    }

    public Object get(String key) {
        return variables.stream()
                .filter(v -> v.getKey().equals(key))
                .map(Variable::getValue)
                .findFirst()
                .orElse(null);
    }
}
