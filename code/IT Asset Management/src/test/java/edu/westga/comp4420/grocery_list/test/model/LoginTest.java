package edu.westga.comp4420.grocery_list.test.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.grocery_list.model.Login;

class LoginTest {

    @Test
    void testValidWestgaEmail() {
        assertTrue(Login.isValidEmail("student@westga.edu"));
    }

    @Test
    void testValidAdminEmail() {
        assertTrue(Login.isValidEmail("admin@admin.edu"));
    }

    @Test
    void testInvalidNonUniversityEmail() {
        assertFalse(Login.isValidEmail("student@gmail.com"));
    }

    @Test
    void testInvalidEmailWithoutRequiredDomain() {
        assertFalse(Login.isValidEmail("student@westga.com"));
    }
}
