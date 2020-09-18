package com.example.greenhouse.sensors;

import java.sql.Timestamp;

public class Sensor {
    private int sensorId;
    private String sensorName;
    private String sensorDescription;
    private String dataType;
    private String dataSize;
    private String sensingRange;
    private String technology;
    private String workingVoltage;
    private String dimensions;
    private String specialFacts;
    private String imageURL;
    private int isDisabled;
    private String lastModifiedUser;
    private Timestamp modifiedTime;

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorDescription() {
        return sensorDescription;
    }

    public void setSensorDescription(String sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public String getSensingRange() {
        return sensingRange;
    }

    public void setSensingRange(String sensingRange) {
        this.sensingRange = sensingRange;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getWorkingVoltage() {
        return workingVoltage;
    }

    public void setWorkingVoltage(String workingVoltage) {
        this.workingVoltage = workingVoltage;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSpecialFacts() {
        return specialFacts;
    }

    public void setSpecialFacts(String specialFacts) {
        this.specialFacts = specialFacts;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
