package com.jarq.system.policy;

public class EmailPolicy implements IEmailPolicy {


    @Override
    public boolean validate(String email) {
        return true;
    }

}
