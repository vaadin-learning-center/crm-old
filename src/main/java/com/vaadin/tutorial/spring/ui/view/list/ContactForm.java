package com.vaadin.tutorial.spring.ui.view.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.tutorial.spring.backend.entity.Company;
import com.vaadin.tutorial.spring.backend.entity.Contact;
import com.vaadin.tutorial.spring.backend.entity.ContactStatus;

import java.util.List;

public class ContactForm extends FormLayout {

  private HasContactEditor parent;

  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private TextField email = new TextField("Email");
  private ComboBox<ContactStatus> status = new ComboBox<>("Status");
  private ComboBox<Company> company = new ComboBox<>("Company");

  private Button save = new Button("Save");
  private Button delete = new Button("Delete");
  private Button close = new Button("Cancel");

  private Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

  public ContactForm(HasContactEditor parent, List<Company> companies) {
    this.parent = parent;
    addClassName("contact-form");

    status.setItems(ContactStatus.values());
    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);

    binder.bindInstanceFields(this);
    add(firstName, lastName, email, company, status, createButtons());
  }

  private Component createButtons() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickShortcut(Key.ENTER);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> parent.deleteContact(binder.getBean()));
    close.addClickListener(event -> parent.closeEditor());

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    if (binder.isValid()) {
      parent.saveContact(binder.getBean());
    }
  }

  public void setContact(Contact contact) {
    binder.setBean(contact);
    if (contact != null) {
      firstName.focus();
    }
  }

  public interface HasContactEditor {
    void saveContact(Contact contact);
    void deleteContact(Contact contact);
    void closeEditor();
  }
}
