package com.kim.jiance.model.auxiliaryinfo;

import java.util.Date;


/**
 * 消控室
 */

public class ControlRoomInfo {

	private String id;//标识
	private String unitId;//所属单位
	private String controlRoomCode;//消控室编号
	private String controlRoomName;//消控室名称
	private Date createTime;//建立时间
	private String roomAddr;//消控室所在地
	private String headName;//负责人

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
	public String getControlRoomCode() {
		return controlRoomCode;
	}
	public void setControlRoomCode(String controlRoomCode) {
		this.controlRoomCode = controlRoomCode;
	}
	public String getControlRoomName() {
		return controlRoomName;
	}
	public void setControlRoomName(String controlRoomName) {
		this.controlRoomName = controlRoomName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRoomAddr() {
		return roomAddr;
	}
	public void setRoomAddr(String roomAddr) {
		this.roomAddr = roomAddr;
	}
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}


}
