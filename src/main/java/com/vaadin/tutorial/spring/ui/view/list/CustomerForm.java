package com.vaadin.tutorial.spring.ui.view.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.tutorial.spring.backend.entity.Company;
import com.vaadin.tutorial.spring.backend.entity.Customer;
import com.vaadin.tutorial.spring.backend.entity.CustomerStatus;

import java.util.List;

public class CustomerForm extends FormLayout {

  private HasCustomerEditor parent;

  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
  private ComboBox<Company> company = new ComboBox<>("Company");

  private Button save = new Button("Save");
  private Button delete = new Button("Delete");
  private Button close = new Button("Close editor");

  private Binder<Customer> binder = new Binder<>(Customer.class);

  public CustomerForm(HasCustomerEditor parent, List<Company> companies) {
    this.parent = parent;
    addClassName("customer-form");

    status.setItems(CustomerStatus.values());
    company.setItems(companies);
    company.setItemLabelGenerator(Company::getName);

    binder.bindInstanceFields(this);
    add(firstName, lastName, company, status, createButtons());
  }

  private Component createButtons() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickShortcut(Key.ENTER);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> parent.saveCustomer(binder.getBean()));
    delete.addClickListener(event -> parent.deleteCustomer(binder.getBean()));
    close.addClickListener(event -> parent.closeEditor());

    HorizontalLayout buttonsLayout = new HorizontalLayout(save, delete, close);
    buttonsLayout.setPadding(true);
    return buttonsLayout;
  }

  public void setCustomer(Customer customer) {
    binder.setBean(customer);
    if (customer != null) {
      firstName.focus();
    }
  }

  public interface HasCustomerEditor {
    void saveCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    void closeEditor();
  }
}
