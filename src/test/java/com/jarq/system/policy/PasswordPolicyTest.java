package com.jarq.system.policy;

import com.jarq.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordPolicyTest extends AbstractTest {


    private IPasswordPolicy passwordPolicy;

    @Before
    public void setup() {
        passwordPolicy = new PasswordPolicy();
    }

    @Test
    public void validate_correct_passwords() {

        String[] passwords = {  "#Omg1231+",
                                "OmgAkOa1@",
                                "$$Greg23++",
                                "##1@@aA!?+++",
                                "+Aa78@@@198",
                                "MaA123#"};

        for(String password : passwords) {
            assertTrue(passwordPolicy.validate(password));
        }
    }

    @Test
    public void validate_incorrect_passwords() {

        String[] passwords = {  "123456789",  // only numbers
                                "1aaaa#aa",  // no any capital letter
                                "@#Adaaad",  // no any number
                                "1aA#1",  // too short
                                "12aaAA113asd!++++aaaaaaaaaaaaaaaaaaBBaaaaaaaaaaaaa",  // too long
                                "MaA123aAa9990"  // no any special character: @#$%+-!
                                };

        for(String password : passwords) {
            assertFalse(passwordPolicy.validate(password));
        }
    }
}
