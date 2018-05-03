package com.jarq.system.enums;

public enum FileExtension {

    REPO, MD;

    public String getExtension() {
        return "." + this.toString().toLowerCase();
    }


}
