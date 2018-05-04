package com.jarq.system.policy;

public class PasswordPolicy implements IPasswordPolicy {


    @Override
    public boolean validate(String password) {
        return false;
    }
}
