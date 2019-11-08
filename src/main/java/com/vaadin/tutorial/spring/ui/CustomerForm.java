package com.vaadin.tutorial.spring.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.tutorial.spring.backend.entity.Company;
import com.vaadin.tutorial.spring.backend.entity.Customer;
import com.vaadin.tutorial.spring.backend.entity.CustomerStatus;
import com.vaadin.tutorial.spring.backend.service.CompanyService;
import com.vaadin.tutorial.spring.backend.service.CustomerService;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
public class CustomerForm extends FormLayout {

  private CustomerService service;

  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
  private ComboBox<Company> company = new ComboBox<>("Company");

  private Button save = new Button("Save");
  private Button delete = new Button("Delete");

  private Binder<Customer> binder = new Binder<>(Customer.class);

  public class CustomersChangedEvent extends ComponentEvent<CustomerForm> {
    public CustomersChangedEvent() {
      super(CustomerForm.this, false);
    }
  }

  public CustomerForm(CustomerService customerService, CompanyService companyService) {
    this.service = customerService;
    status.setItems(CustomerStatus.values());
    company.setItems(companyService.findAll());
    company.setItemLabelGenerator(Company::getName);

    HorizontalLayout buttons = new HorizontalLayout(save, delete);
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    add(firstName, lastName, company, status, buttons);
    binder.bindInstanceFields(this);

    save.addClickListener(event -> save());
    delete.addClickListener(event -> delete());
  }


  public void setCustomer(Customer customer) {
    binder.setBean(customer);

    if (customer == null) {
      setVisible(false);
    } else {
      setVisible(true);
      firstName.focus();
    }
  }

  private void save() {
    Customer customer = binder.getBean();
    service.save(customer);
    fireEvent(new CustomersChangedEvent());
    setCustomer(null);
  }

  private void delete() {
    Customer customer = binder.getBean();
    service.delete(customer);
    fireEvent(new CustomersChangedEvent());
    setCustomer(null);
  }

  public Registration addCustomersChangedListener(ComponentEventListener<CustomersChangedEvent> listener) {
    return addListener(CustomersChangedEvent.class, listener);
  }
}
