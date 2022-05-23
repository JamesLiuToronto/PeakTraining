package com.peak.training.common.domain.valueobject;

import com.peak.training.common.domain.base.ValueObject;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@Jacksonized
public class EmailAddress implements ValueObject { // <1>

    private final String email; // <2>

    public EmailAddress(@NotNull String email) {
        this.email = validate(email); // <3>
    }

    public String getEmail(){
        return email ;
    }

    @Override
    public @NotNull String toString() { // <4>
        return email;
    }

    @Override
    public boolean equals(Object o) { // <5>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() { // <6>
        return email.hashCode();
    }

    public static @NotNull String validate(@NotNull String email) { // <7>
        if (!isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return email;
    }


    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public static boolean isValid(@NotNull String email) { // <8>
        // Validate the input string, return true or false depending on whether it is a valid e-mail address or not
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
