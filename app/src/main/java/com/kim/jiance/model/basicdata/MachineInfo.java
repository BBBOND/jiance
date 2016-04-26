package com.kim.jiance.model.basicdata;


/**
 * 设备表
 */

public class MachineInfo {

    private String id;//标识
    private String unitId;//所属单位
    private String machineCode;//设备编号
    private String machineName;//设备名称
    private String machineType;//设备类型
    private Integer machineIndex;//设备序号
    private String maker;//制造商
    private String machineVersion;//设备型号
    private Boolean isUsed;//是否可用

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

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public Integer getMachineIndex() {
        return machineIndex;
    }

    public void setMachineIndex(Integer machineIndex) {
        this.machineIndex = machineIndex;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMachineVersion() {
        return machineVersion;
    }

    public void setMachineVersion(String machineVersion) {
        this.machineVersion = machineVersion;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MachineInfo that = (MachineInfo) o;

        return (id.equals(that.id));

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + unitId.hashCode();
        result = 31 * result + machineCode.hashCode();
        result = 31 * result + machineName.hashCode();
        result = 31 * result + machineType.hashCode();
        result = 31 * result + machineIndex.hashCode();
        result = 31 * result + maker.hashCode();
        result = 31 * result + machineVersion.hashCode();
        result = 31 * result + isUsed.hashCode();
        return result;
    }
}
