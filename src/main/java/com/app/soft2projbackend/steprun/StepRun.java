package com.app.soft2projbackend.steprun;

import java.time.Instant;
import java.util.UUID;

public class StepRun {
    private final String id;
    private final String nodeId;

    private StepStatus status;
    private Instant startTime;
    private Instant endTime;

    private String output;
    private String error;

    public StepRun(String nodeId) {
        this.id = UUID.randomUUID().toString();
        this.nodeId = nodeId;
        this.status = StepStatus.PENDING;
    }

    public void markStart() {
        this.status = StepStatus.RUNNING;
        this.startTime = Instant.now();
    }

    public void markEnd(StepStatus status) {
        this.status = status;
        this.endTime = Instant.now();
    }


    public void setOutput(String output) {
        this.output = output;
    }

    public void setError(String error) {
        this.error = error;
    }



    public String getId() {
        return id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public StepStatus getStatus() {
        return status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }
}
