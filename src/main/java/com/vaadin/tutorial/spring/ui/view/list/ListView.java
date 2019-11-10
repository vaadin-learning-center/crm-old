package com.vaadin.tutorial.spring.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.spring.backend.entity.Contact;
import com.vaadin.tutorial.spring.backend.service.CompanyService;
import com.vaadin.tutorial.spring.backend.service.ContactService;
import com.vaadin.tutorial.spring.ui.view.MainView;

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
    grid.getColumns().forEach(col -> col.setAutoWidth(true));
    grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
    grid.asSingleSelect().addValueChangeListener(event ->
        editContact(grid.asSingleSelect().getValue()));
  }

  private HorizontalLayout getToolbar() {
    filterText.setPlaceholder("Filter by name...");
    filterText.setClearButtonVisible(true);
    filterText.setValueChangeMode(ValueChangeMode.LAZY);
    filterText.addValueChangeListener(e -> updateList());

    Button addContactButton = new Button("Add new contact");
    addContactButton.addClickListener(e -> {
      grid.asSingleSelect().clear();
      editContact(new Contact());
    });

    HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
    toolbar.addClassName("toolbar");
    return toolbar;
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
