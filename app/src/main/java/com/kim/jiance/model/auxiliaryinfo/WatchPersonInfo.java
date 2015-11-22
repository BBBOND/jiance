package com.kim.jiance.model.auxiliaryinfo;

/**
 * 值班人员表
 */


public class WatchPersonInfo {

	private String id;//标识
	private String unitId;//所属单位
	private String watchPersonName;//姓名
	private String contactTel;//联系电话
	private String personId;//身份证
	private String workCard;//上岗证


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

	public String getWatchPersonName() {
		return watchPersonName;
	}
	public void setWatchPersonName(String watchPersonName) {
		this.watchPersonName = watchPersonName;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getWorkCard() {
		return workCard;
	}
	public void setWorkCard(String workCard) {
		this.workCard = workCard;
	}
}
