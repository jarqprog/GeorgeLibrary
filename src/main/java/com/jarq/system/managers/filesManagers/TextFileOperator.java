package com.jarq.system.managers.filesManagers;

import java.nio.charset.Charset;

public abstract class TextFileOperator {

    private final Charset ENCODING;

    protected TextFileOperator(Charset charset) {
        this.ENCODING = charset;
    }

    protected Charset getEncoding() {
        return ENCODING;
    }
}
