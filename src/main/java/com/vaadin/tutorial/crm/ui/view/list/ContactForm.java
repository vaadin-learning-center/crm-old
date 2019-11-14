package com.vaadin.tutorial.crm.ui.view.list;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.entity.ContactStatus;

public class ContactForm extends FormLayout {

  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private TextField email = new TextField("Email");
  private ComboBox<ContactStatus> status = new ComboBox<>("Status");
  private ComboBox<Company> company = new ComboBox<>("Company");

  private Button save = new Button("Save");
  private Button delete = new Button("Delete");
  private Button close = new Button("Cancel");

  private Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

  public ContactForm(List<Company> companies) {
    addClassName("contact-form");

    status.setItems(ContactStatus.values());
    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);

    binder.bindInstanceFields(this);
    firstName.setId("firstName");
    lastName.setId("lastName");
    email.setId("email");
    status.setId("status");
    company.setId("company");
    save.setId("save");
    delete.setId("delete");
    close.setId("close");
    add(firstName, lastName, email, company, status, createButtons());
  }

  private Component createButtons() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickShortcut(Key.ENTER);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> getEventBus().fireEvent(new DeleteEvent(this, binder.getBean())));
    close.addClickListener(event -> getEventBus().fireEvent(new CloseEvent(this)));

    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
    return new HorizontalLayout(save, delete, close);
  }

  private void validateAndSave() {
    if (binder.isValid()) {
      getEventBus().fireEvent(new SaveEvent(this, binder.getBean()));
    }
  }

  public void setContact(Contact contact) {
    binder.setBean(contact);
    if (contact != null) {
      firstName.focus();
    }
  }

  public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
    private Contact contact;

    protected ContactFormEvent(ContactForm source, Contact contact) {
      super(source, false);
      this.contact = contact;
    }

    public Contact getContact() {
      return contact;
    }
  }

  public static class SaveEvent extends ContactFormEvent {
    SaveEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }
  }

  public static class DeleteEvent extends ContactFormEvent {
    DeleteEvent(ContactForm source, Contact contact) {
      super(source, contact);
    }

  }

  public static class CloseEvent extends ContactFormEvent {
    CloseEvent(ContactForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
      ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }

}
