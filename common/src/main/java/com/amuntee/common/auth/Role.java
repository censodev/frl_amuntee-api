package com.amuntee.common.auth;

public enum Role {
    ADMIN(1, "ROLE_ADMIN"),
    STAFF(2, "ROLE_STAFF");

    private final int index;
    private final String title;

    Role(int i, String title) {
        index = i;
        this.title = title;
    }

    public int index() {
        return index;
    }
    public String title() {
        return title;
    }
}
