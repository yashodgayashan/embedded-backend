package com.example.greenhouse.nodes;

import java.sql.Timestamp;

public class NodeSensor {

    private int nodeSensorId;
    private int nodeId;
    private int sensorId;
    private int isDisabled;
    private String lastModifiedUser;
    private Timestamp modifiedTime;

    public int getNodeSensorId() {
        return nodeSensorId;
    }

    public void setNodeSensorId(int nodeSensorId) {
        this.nodeSensorId = nodeSensorId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
