package com.kim.jiance.model.more;

import java.io.Serializable;

public class SimpleFireControlMess implements Serializable {

    private String unitid;
    private String unitcode;
    private String unitname;
    private int evetcount;
    private int totlafailure;
    private double equipmentintegrityrate;

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public int getEvetcount() {
        return evetcount;
    }

    public void setEvetcount(int evetcount) {
        this.evetcount = evetcount;
    }

    public int getTotlafailure() {
        return totlafailure;
    }

    public void setTotlafailure(int totlafailure) {
        this.totlafailure = totlafailure;
    }

    public double getEquipmentintegrityrate() {
        return equipmentintegrityrate;
    }

    public void setEquipmentintegrityrate(double equipmentintegrityrate) {
        this.equipmentintegrityrate = equipmentintegrityrate;
    }


}
