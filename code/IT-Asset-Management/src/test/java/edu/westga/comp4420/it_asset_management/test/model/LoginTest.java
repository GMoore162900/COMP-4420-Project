package edu.westga.comp4420.it_asset_management.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.it_asset_management.model.Login;

class LoginTest {

    @Test
    void testValidConstructorStoresTrimmedEmailAndPassword() {
        Login login = new Login("  admin@admin.edu  ", "password123");

        assertEquals("admin@admin.edu", login.getEmail());
        assertEquals("password123", login.getPassword());
    }

    @Test
    void testValidAdminEmail() {
        assertTrue(Login.isValidEmail("admin@admin.edu"));
    }

    @Test
    void testValidAdminEmailWithUppercaseCharacters() {
        assertTrue(Login.isValidEmail("ADMIN@ADMIN.EDU"));
    }

    @Test
    void testInvalidNullEmail() {
        assertFalse(Login.isValidEmail(null));
    }

    @Test
    void testInvalidBlankEmail() {
        assertFalse(Login.isValidEmail("   "));
    }

    @Test
    void testInvalidWestgaEmail() {
        assertFalse(Login.isValidEmail("student@westga.edu"));
    }

    @Test
    void testInvalidNonAdminEmail() {
        assertFalse(Login.isValidEmail("student@gmail.com"));
    }

    @Test
    void testInvalidOtherDomain() {
        assertFalse(Login.isValidEmail("student@westga.com"));
    }

    @Test
    void testConstructorRejectsInvalidEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> new Login("student@westga.edu", "password123"));
    }

    @Test
    void testConstructorRejectsNullPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> new Login("admin@admin.edu", null));
    }

    @Test
    void testConstructorRejectsBlankPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> new Login("admin@admin.edu", "   "));
    }
}
