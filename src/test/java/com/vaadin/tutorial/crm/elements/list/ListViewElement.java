package com.vaadin.tutorial.crm.elements.list;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.annotations.Attribute;

@Attribute(name = "class", contains = "list-view")
public class ListViewElement extends VerticalLayoutElement {

    public GridElement getContactsGrid() {
        return $(GridElement.class).attributeContains("class", "contact-grid").first();
    }

    public ContactsFormElement getForm() {
        return $(ContactsFormElement.class).first();
    }

}
