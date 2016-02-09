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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleFireControlMess that = (SimpleFireControlMess) o;

        if (evetcount != that.evetcount) return false;
        if (totlafailure != that.totlafailure) return false;
        if (Double.compare(that.equipmentintegrityrate, equipmentintegrityrate) != 0) return false;
        if (!unitid.equals(that.unitid)) return false;
        if (!unitcode.equals(that.unitcode)) return false;
        return unitname.equals(that.unitname);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = unitid.hashCode();
        result = 31 * result + unitcode.hashCode();
        result = 31 * result + unitname.hashCode();
        result = 31 * result + evetcount;
        result = 31 * result + totlafailure;
        temp = Double.doubleToLongBits(equipmentintegrityrate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
