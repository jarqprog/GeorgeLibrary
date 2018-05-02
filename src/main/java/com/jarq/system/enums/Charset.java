package com.jarq.system.enums;

public enum Charset {

    UTF8("UTF-8");

    private String charset;

    Charset(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

}
