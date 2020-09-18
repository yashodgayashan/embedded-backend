package com.example.greenhouse.data;

import java.sql.Timestamp;

public class DualData {

    private int dataId;
    private int nodeId;
    private float data1;
    private float data2;
    private int isDisabled;
    private int validated;
    private Timestamp savedDateTime;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public float getData1() {
        return data1;
    }

    public void setData1(float data1) {
        this.data1 = data1;
    }

    public float getData2() {
        return data2;
    }

    public void setData2(float data2) {
        this.data2 = data2;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public Timestamp getSavedDateTime() {
        return savedDateTime;
    }

    public void setSavedDateTime(Timestamp savedDateTime) {
        this.savedDateTime = savedDateTime;
    }

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
        this.validated = validated;
    }
}
