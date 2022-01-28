package com.inspien.pilot.manager;

import java.util.Locale;

public class PolicyEntry {
    private String accessType; //allow, deny
    private String permissionType; //FilePermission ...
    private String permissionName; //"file path"
    private String permissionAction; // "READ, WRITE" "GET" ...

    public PolicyEntry(String accessType, String permissionType, String permissionName, String permissionAction) {
        this.accessType = accessType.toLowerCase();
        this.permissionType = permissionType;
        this.permissionName = permissionName;
        this.permissionAction = permissionAction;
    }

    public String getAccessType() {
        return accessType;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionAction() {
        return permissionAction;
    }
}
