package com.jarq.system.service;

import java.util.Arrays;

public abstract class Service implements IService {

    protected String getStackTrace() {
        return Arrays.toString(Thread.currentThread().getStackTrace());
    }
}
