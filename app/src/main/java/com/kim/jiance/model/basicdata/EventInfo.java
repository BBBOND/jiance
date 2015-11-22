package com.kim.jiance.model.basicdata;

// Generated 2015-5-7 9:42:43 by Hibernate Tools 3.4.0.CR1


/**
 * 事件表
 */

public class EventInfo {

    private String id;//标识
    private String unitId;//所属单位
    private String handleUserId;//处理人
    private String eventTypeId;//事件类型ID
    private String unitName;//部门名称
    private String unitAddr;//部门地址
    private String contactTel;//联系人电话
    private String machineCode;//设备编码
    private String alertMachine;//报警设备
    private String alertSource;//报警源
    private String alertDescription;//描述信息
    private String happenPlace;//发生地点
    private String eventName;//事件名称
    private String happenTime;//发生时间
    private String eventDescription;//事件说明
    private String handleDescription;//处理说明
    private String handleTime;//处理时间
    private Boolean isHandle;//是否处理
    private Boolean isFalseReport;//是否误报


    public EventInfo() {

    }

    public EventInfo(String unitId, String handleUserId, String eventTypeId,
                     String unitName, String unitAddr, String contactTel,
                     String machineCode, String alertMachine, String alertSource,
                     String alertDescription, String happenPlace, String eventName,
                     String happenTime, String eventDescription,
                     String handleDescription, String handleTime, Boolean isHandle,
                     Boolean isFalseReport) {
        this.unitId = unitId;
        this.handleUserId = handleUserId;
        this.eventTypeId = eventTypeId;
        this.unitName = unitName;
        this.unitAddr = unitAddr;
        this.contactTel = contactTel;
        this.machineCode = machineCode;
        this.alertMachine = alertMachine;
        this.alertSource = alertSource;
        this.alertDescription = alertDescription;
        this.happenPlace = happenPlace;
        this.eventName = eventName;
        this.happenTime = happenTime;
        this.eventDescription = eventDescription;
        this.handleDescription = handleDescription;
        this.handleTime = handleTime;
        this.isHandle = isHandle;
        this.isFalseReport = isFalseReport;
    }

    public EventInfo(String id, String unitId, String handleUserId,
                     String eventTypeId, String unitName, String unitAddr,
                     String contactTel, String machineCode, String alertMachine,
                     String alertSource, String alertDescription, String happenPlace,
                     String eventName, String happenTime, String eventDescription,
                     String handleDescription, String handleTime, Boolean isHandle,
                     Boolean isFalseReport) {
        this.id = id;
        this.unitId = unitId;
        this.handleUserId = handleUserId;
        this.eventTypeId = eventTypeId;
        this.unitName = unitName;
        this.unitAddr = unitAddr;
        this.contactTel = contactTel;
        this.machineCode = machineCode;
        this.alertMachine = alertMachine;
        this.alertSource = alertSource;
        this.alertDescription = alertDescription;
        this.happenPlace = happenPlace;
        this.eventName = eventName;
        this.happenTime = happenTime;
        this.eventDescription = eventDescription;
        this.handleDescription = handleDescription;
        this.handleTime = handleTime;
        this.isHandle = isHandle;
        this.isFalseReport = isFalseReport;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAddr() {
        return unitAddr;
    }

    public void setUnitAddr(String unitAddr) {
        this.unitAddr = unitAddr;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
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

    public String getAlertSource() {
        return alertSource;
    }

    public void setAlertSource(String alertSource) {
        this.alertSource = alertSource;
    }

    public String getAlertDescription() {
        return alertDescription;
    }

    public void setAlertDescription(String alertDescription) {
        this.alertDescription = alertDescription;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getHandleDescription() {
        return handleDescription;
    }

    public void setHandleDescription(String handleDescription) {
        this.handleDescription = handleDescription;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public Boolean getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Boolean isHandle) {
        this.isHandle = isHandle;
    }

    public Boolean getIsFalseReport() {
        return isFalseReport;
    }

    public void setIsFalseReport(Boolean isFalseReport) {
        this.isFalseReport = isFalseReport;
    }
}
