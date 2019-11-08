package com.vaadin.tutorial.spring.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.spring.backend.entity.Customer;
import com.vaadin.tutorial.spring.backend.service.CustomerService;
import org.springframework.stereotype.Component;

@Route("")
//@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
@Component
public class MainView extends VerticalLayout {

    private CustomerService service;

    Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();


    public MainView(CustomerService service, CustomerForm form) {
        this.service = service;
        setSizeFull();

        grid.setColumns("firstName", "lastName", "status");
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        add(
            new HorizontalLayout(filterText, addCustomerBtn),
            new Div(grid, form){{setSizeFull();}});

        updateList();
        form.addCustomersChangedListener(e -> updateList());
        form.setCustomer(null);
        grid.asSingleSelect().addValueChangeListener(event ->
            form.setCustomer(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
