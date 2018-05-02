package com.jarq.system.enums;

public enum Charsets {

    UTF8("UTF-8");

    private String charset;

    Charsets(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

}
