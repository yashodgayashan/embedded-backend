package com.example.greenhouse.nodes;

import java.sql.Timestamp;

public class Node {

    private int nodeId;
    private int parentNodeId;
    private int createdUserId;
    private int disabled;
    private String lastModifiedUser;
    private Timestamp modifiedTime;

    public int getNodeId() {
        return nodeId;
    }

    public int getParentNodeId() {
        return parentNodeId;
    }

    public int getCreatedUserId() {
        return createdUserId;
    }

    public int getDisabled() {
        return disabled;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public void setCreatedUserId(int createdUserId) {
        this.createdUserId = createdUserId;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
