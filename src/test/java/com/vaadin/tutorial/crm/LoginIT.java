package com.vaadin.tutorial.crm;

import com.vaadin.tutorial.crm.elements.list.ListViewElement;
import com.vaadin.tutorial.crm.elements.login.LoginViewElement;
import com.vaadin.tutorial.crm.util.AbstractTest;

import org.junit.Assert;
import org.junit.Test;

public class LoginIT extends AbstractTest {

    public LoginIT() {
        super("");
    }

    @Test
    public void loginAsValidUserSucceeds() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("user", "password"));
        // Ensure we end up on the list page
        Assert.assertTrue($(ListViewElement.class).onPage().exists());
    }

    @Test
    public void loginAsInvalidUserFails() {
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertFalse(loginView.login("foo", "bar"));
        // Ensure we stay on the login view
        Assert.assertTrue($(LoginViewElement.class).onPage().exists());
    }
}
