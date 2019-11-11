package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.service.CompanyService;
import com.vaadin.tutorial.crm.backend.service.ContactService;
import com.vaadin.tutorial.crm.ui.view.MainView;

@Route(value = "", layout = MainView.class)
public class ListView extends VerticalLayout implements ContactForm.HasContactEditor {

  private ContactService service;

  private Grid<Contact> grid = new Grid<>(Contact.class);
  private TextField filterText = new TextField();
  private ContactForm form;


  public ListView(ContactService contactService, CompanyService companyService) {
    this.service = contactService;

    setSizeFull();
    addClassName("list-view");
    configureGrid();

    form = new ContactForm(this, companyService.findAll());

    HorizontalLayout toolbar = getToolbar();

    Div content = new Div(grid, form);
    content.addClassName("content");
    content.setSizeFull();

    add(toolbar, content);

    closeEditor();
    updateList();
  }

  private void configureGrid() {
    grid.addClassName("contact-grid");
    grid.setSizeFull();
    grid.removeColumnByKey("company");
    grid.setColumns("firstName", "lastName", "email", "status");
    grid.addColumn(contact -> {
      Company company = contact.getCompany();
      return company == null ? "-" : company.getName();
    }).setHeader("Company");
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.asSingleSelect().addValueChangeListener(event ->
        editContact(grid.asSingleSelect().getValue()));
  }

  private HorizontalLayout getToolbar() {
    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(e -> updateList());

    Button addContactButton = new Button("Add new contact");
    addContactButton.addClickListener(click -> addContact());

    Button importButton = new Button("Import leads", click -> importLeads());
    importButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton, importButton);
    toolbar.addClassName("toolbar");
    return toolbar;
  }

  private void importLeads() {
    service.importContacts();
    updateList();
    Notification notification = new Notification("Import successful");
    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    notification.setDuration(3000);
    notification.open();
  }

  void addContact() {
    grid.asSingleSelect().clear();
    editContact(new Contact());
  }

  public void updateList() {
    grid.setItems(service.findAll(filterText.getValue()));
  }

  public void editContact(Contact contact) {
    if (contact == null) {
      closeEditor();
    } else {
      form.setContact(contact);
      form.setVisible(true);
      addClassName("editing");
    }
  }

  @Override
  public void saveContact(Contact contact) {
    service.save(contact);
    updateList();
    closeEditor();
  }

  @Override
  public void deleteContact(Contact contact) {
    service.delete(contact);
    updateList();
    closeEditor();
  }

  @Override
  public void closeEditor() {
    form.setContact(null);
    form.setVisible(false);
    removeClassName("editing");
  }
}
