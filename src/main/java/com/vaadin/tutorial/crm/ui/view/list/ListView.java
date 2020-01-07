package com.vaadin.tutorial.crm.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Contact;
import com.vaadin.tutorial.crm.backend.service.CompanyService;
import com.vaadin.tutorial.crm.backend.service.ContactService;
import com.vaadin.tutorial.crm.ui.view.MainLayout;
import com.vaadin.tutorial.crm.ui.view.list.ContactForm.CloseEvent;
import com.vaadin.tutorial.crm.ui.view.list.ContactForm.DeleteEvent;
import com.vaadin.tutorial.crm.ui.view.list.ContactForm.SaveEvent;

@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {

  private ContactService contactService;

  private Grid<Contact> grid = new Grid<>(Contact.class);
  private TextField filterText = new TextField();
  private ContactForm form;

  public ListView(ContactService contactService, CompanyService companyService) {
    this.contactService = contactService;

    setSizeFull();
    addClassName("list-view");
    configureGrid();

    form = new ContactForm(companyService.findAll());
    form.addListener(SaveEvent.class, this::saveContact);
    form.addListener(DeleteEvent.class, this::deleteContact);
    form.addListener(CloseEvent.class, e -> this.closeEditor());
    closeEditor();

    HorizontalLayout toolbar = getToolbar();

    Div content = new Div(grid, form);
    content.addClassName("content");
    content.setSizeFull();

    add(toolbar, content);

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

  grid.asSingleSelect().addValueChangeListener(event -> editContact(grid.asSingleSelect().getValue()));
}

  private HorizontalLayout getToolbar() {
    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(e -> updateList());

    Button addContactButton = new Button("Add contact");
    addContactButton.addClickListener(click -> addContact());

    HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
    toolbar.addClassName("toolbar");
    return toolbar;
  }

  void addContact() {
    grid.asSingleSelect().clear();
    editContact(new Contact());
  }

  public void updateList() {
    grid.setItems(contactService.findAll(filterText.getValue()));
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

private void saveContact(SaveEvent event) {
  contactService.save(event.getContact());
  updateList();
  closeEditor();
}

private void deleteContact(DeleteEvent event) {
  contactService.delete(event.getContact());
  updateList();
  closeEditor();
}

  private void closeEditor() {
    form.setContact(null);
    form.setVisible(false);
    removeClassName("editing");
  }
}
