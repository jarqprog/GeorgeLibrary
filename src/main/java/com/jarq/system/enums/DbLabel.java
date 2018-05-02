package com.jarq.system.enums;

public enum DbLabel {

    ID;

    public String getLabel() {
       return this.toString().toLowerCase();
    }
}
