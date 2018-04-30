package com.jarq.system.enums;

public enum DbLabels {

    ID;

    public String getLabel() {
       return this.toString().toLowerCase();
    }
}
