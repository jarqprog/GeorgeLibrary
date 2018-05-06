package com.jarq.system.policy;

import com.jarq.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmailPolicyTest extends AbstractTest {

    private IEmailPolicy emailPolicy;

    @Before
    public void setup() {
        emailPolicy = new EmailPolicy();
    }

    @Test
    public void validate_correct_emails() {

        String[] emailsToValidate = {   "mark@gmail.com", "tom@m23.pl", "an12.jar-tom@aaaaak.ru",
                                        "hel-oxy12321.21@r.pl", "webmaster@moeller.de", "ANAo23@j.pl"};

        for(String email : emailsToValidate) {
            assertTrue(emailPolicy.validate(email));
        }
    }

    @Test
    public void validate_incorrect_emails() {

        String[] emailsToValidate = {   "?mark@gmail.com", "tom23.pl", "an12.jar-tom@aaaaak",
                "hel-oxy12321.21@r!pl", "webmaster@moeller,de", "AN@Ao23@j.pl"};

        for(String email : emailsToValidate) {
            assertFalse(emailPolicy.validate(email));
        }
    }

}