package com.vaadin.tutorial.crm.it.elements.list;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.testbench.ComboBoxElement;
import com.vaadin.flow.component.formlayout.testbench.FormLayoutElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.annotations.Attribute;

@Attribute(name = "class", contains = "contact-form")
public class ContactsFormElement extends FormLayoutElement {

    public TextFieldElement getFirstNameField() {
        return $(TextFieldElement.class).id("firstName");
    }

    public TextFieldElement getLastNameField() {
        return $(TextFieldElement.class).id("lastName");
    }

    public TextFieldElement getEmailField() {
        return $(TextFieldElement.class).id("email");
    }

    public ComboBoxElement getStatusField() {
        return $(ComboBoxElement.class).id("status");
    }

    public ComboBoxElement getCompanyField() {
        return $(ComboBoxElement.class).id("company");
    }

    public ButtonElement getSaveButton() {
        return $(ButtonElement.class).id("save");
    }

    public ButtonElement getDeleteButton() {
        return $(ButtonElement.class).id("delete");
    }

    public ButtonElement getCloseButton() {
        return $(ButtonElement.class).id("close");
    }

}
