package com.kim.jiance.model.basicdata;

// Generated 2015-5-7 9:42:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * 记录表
 */

public class RecordInfo {

    private String id;//标识
    private String machineCode;//设备编号
    private String alertMachine;//报警设备
    private Date alertTime;//报警时间
    private String alertSource;//报警源
    private Integer port;//端口
    private Double signalValue;//信号值
    private String description;//描述
    private String happenPlace;//发生地点
    private String expressionKey;//公式关键字


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getAlertMachine() {
        return alertMachine;
    }

    public void setAlertMachine(String alertMachine) {
        this.alertMachine = alertMachine;
    }

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public String getAlertSource() {
        return alertSource;
    }

    public void setAlertSource(String alertSource) {
        this.alertSource = alertSource;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Double getSignalValue() {
        return signalValue;
    }

    public void setSignalValue(Double signalValue) {
        this.signalValue = signalValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getExpressionKey() {
        return expressionKey;
    }

    public void setExpressionKey(String expressionKey) {
        this.expressionKey = expressionKey;
    }
}
