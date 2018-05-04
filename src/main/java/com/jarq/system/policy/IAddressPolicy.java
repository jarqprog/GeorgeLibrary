package com.jarq.system.policy;

public interface IAddressPolicy {

    boolean validatePostalCode(String postalCode);
    boolean validate(String postalCode);
}
