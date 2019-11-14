package com.vaadin.tutorial.crm;

import com.vaadin.flow.component.grid.testbench.GridColumnElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.tutorial.crm.elements.list.ContactsFormElement;
import com.vaadin.tutorial.crm.elements.list.ListViewElement;
import com.vaadin.tutorial.crm.ui.view.list.ListView;
import com.vaadin.tutorial.crm.util.AbstractViewTest;

import org.junit.Assert;
import org.junit.Test;

public class ListViewIT extends AbstractViewTest {

    public ListViewIT() {
        super(ListView.class);
    }

    @Test
    public void selectingInGridShowsForm() {
        ListViewElement listView = $(ListViewElement.class).onPage().first();
        GridElement grid = listView.getContactsGrid();
        GridColumnElement firstNameColumn = grid.getColumn("First Name");
        String firstContactFirstName = grid.getRow(0).getCell(firstNameColumn).getText();
        GridColumnElement lastNameColumn = grid.getColumn("Last Name");
        String firstContactLastName = grid.getRow(0).getCell(lastNameColumn).getText();

        Assert.assertEquals("Gabrielle", firstContactFirstName);
        Assert.assertEquals("Patel", firstContactLastName);
        grid.select(0);
        ContactsFormElement form = listView.getForm();
        Assert.assertEquals(firstContactFirstName, form.getFirstNameField().getValue());
        Assert.assertEquals(firstContactLastName, form.getLastNameField().getValue());
    }

}