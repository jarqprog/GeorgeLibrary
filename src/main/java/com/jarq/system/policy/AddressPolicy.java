package com.jarq.system.policy;

public class AddressPolicy implements IAddressPolicy {

    @Override
    public boolean validatePostalCode(String postalCode) {
        return true;
    }

    @Override
    public boolean validate(String postalCode) {
        return true;
    }
}
