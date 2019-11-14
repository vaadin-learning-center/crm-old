package com.vaadin.tutorial.crm.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.internal.AnnotationReader;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.elements.login.LoginViewElement;

import org.junit.Assert;

public abstract class AbstractViewTest extends AbstractTest {

    protected AbstractViewTest(Class<? extends Component> view) {
        super(getRoute(view));
    }

    private static String getRoute(Class<? extends Component> view) {
        return AnnotationReader.getAnnotationFor(view, Route.class).get().value();
    }

    @Override
    public void setup() throws Exception {
        super.setup();
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
        Assert.assertTrue(loginView.login("user", "password"));
    }
}