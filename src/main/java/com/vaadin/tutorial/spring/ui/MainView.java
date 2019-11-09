package com.vaadin.tutorial.spring.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.spring.backend.entity.Customer;
import com.vaadin.tutorial.spring.backend.service.CompanyService;
import com.vaadin.tutorial.spring.backend.service.CustomerService;

@Route("")
//@PWA(name = "VaadinCRM", shortName = "VaadinCRM")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout implements CustomerForm.HasCustomerEditor {

  private CustomerService service;

  private Grid<Customer> grid = new Grid<>(Customer.class);
  private TextField filterText = new TextField();
  private CustomerForm form;


  public MainView(CustomerService customerService, CompanyService companyService) {
    this.service = customerService;

    setSizeFull();
    addClassName("main-view");
    configureGrid();

    form = new CustomerForm(this, companyService.findAll());

    Div content = new Div(grid, form);
    content.addClassName("content");
    content.setSizeFull();

    add(getToolbar(), content);

    closeEditor();
    updateList();
  }

  private void configureGrid() {
    grid.addClassName("customer-grid");
    grid.setSizeFull();
    grid.removeColumnByKey("company");
    grid.setColumns("firstName", "lastName", "email", "status");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.addColumn(customer -> customer.getCompany().getName()).setHeader("Company");
    grid.asSingleSelect().addValueChangeListener(event ->
        editCustomer(grid.asSingleSelect().getValue()));
  }

  private HorizontalLayout getToolbar() {
    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(e -> updateList());

    Button addCustomerBtn = new Button("Add new customer");
    addCustomerBtn.addClickListener(e -> {
      grid.asSingleSelect().clear();
      form.setCustomer(new Customer());
    });

    HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerBtn);
    toolbar.addClassName("toolbar");
    return toolbar;
  }

  public void updateList() {
    grid.setItems(service.findAll(filterText.getValue()));
  }

  public void editCustomer(Customer customer) {
    if (customer == null) {
      closeEditor();
    } else {
      form.setCustomer(grid.asSingleSelect().getValue());
      form.setVisible(true);
      addClassName("editing");
    }
  }

  @Override
  public void saveCustomer(Customer customer) {
    service.save(customer);
    updateList();
    closeEditor();
  }

  @Override
  public void deleteCustomer(Customer customer) {
    service.delete(customer);
    updateList();
    closeEditor();
  }

  @Override
  public void closeEditor() {
    form.setCustomer(null);
    form.setVisible(false);
    removeClassName("editing");
  }
}
