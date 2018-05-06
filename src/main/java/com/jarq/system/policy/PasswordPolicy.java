package com.jarq.system.policy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicy implements IPasswordPolicy {

    /**
     *  source: http://www.mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
     *  (slightly modified)
     (			# Start of group

     (?=.*\d)		#   must contains one digit from 0-9
     (?=.*[a-z])		#   must contains one lowercase characters
     (?=.*[A-Z])		#   must contains one uppercase characters
     (?=.*[@#$%+-!])		#   must contains one special symbols in the list "@#$%"
     .		#     match anything with previous condition checking
     {6,20}	#        length at least 6 characters and maximum of 20

     )			# End of group
     *
     */

    private final Pattern VALID_PASSWORD_REGEX = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    @Override
    public boolean validate(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX .matcher(password);
        return matcher.find();
    }
}