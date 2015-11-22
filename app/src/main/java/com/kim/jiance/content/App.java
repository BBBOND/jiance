package com.kim.jiance.content;

import android.app.Application;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class App extends Application {
    private static String userID = "";
    private static String roleName = "";
    private static String unitID = "";

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        App.userID = userID;
    }

    public static String getRoleName() {
        return roleName;
    }

    public static void setRoleName(String roleName) {
        App.roleName = roleName;
    }

    public static String getUnitID() {
        return unitID;
    }

    public static void setUnitID(String unitID) {
        App.unitID = unitID;
    }
}
