package com.jarq.system.managers.filesManagers;

import java.nio.charset.Charset;

public abstract class RepositoryFile {

    private final Charset ENCODING;

    protected RepositoryFile(Charset charset) {
        this.ENCODING = charset;
    }

    protected Charset getEncoding() {
        return ENCODING;
    }
}
