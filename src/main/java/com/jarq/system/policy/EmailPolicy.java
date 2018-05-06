package com.jarq.system.policy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPolicy implements IEmailPolicy {

    /**
     * source: https://stackoverflow.com/questions/8204680/java-regex-email
     */

    private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    @Override
    public boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
}