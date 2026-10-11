package edu.westga.comp4420.it_asset_management.model;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Represents a single login attempt for a university account.
 *
 * @author Comp 4420
 * @version Fall 2026
 */
public class Login {
    private static final Pattern VALID_EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@(?:admin)\\.edu$");

    private final String email;
    private final String password;

    /**
     * Create a login with the provided email and password.
     *
     * @precondition email is valid and password is not blank
     * @postcondition getEmail() == email && getPassword() == password
     *
     * @param email the university email address
     * @param password the password for the account
     */
    public Login(String email, String password) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Use an email ending in @admin.edu.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        this.email = email.trim();
        this.password = password;
    }

    /**
     * Return the email address.
     *
     * @return the email address for the login
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Return the password.
     *
     * @return the password for the login
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Check whether the provided email address is valid for this application.
     *
     * @param email the email to validate
     * @return true when the email ends with an approved university domain
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) {
            return false;
        }

        return VALID_EMAIL_PATTERN.matcher(trimmedEmail.toLowerCase(Locale.US)).matches();
    }
}
