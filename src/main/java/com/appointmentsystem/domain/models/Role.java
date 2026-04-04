package com.appointmentsystem.domain.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Role {
    private final String name;
    private final Set<String> permissions;

    public Role(String name) {
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public void removePermission(String permission) {
        permissions.remove(permission);
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public Set<String> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public String getRole() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{name='" + name + "', permissions=" + permissions.size() + "}";
    }
}