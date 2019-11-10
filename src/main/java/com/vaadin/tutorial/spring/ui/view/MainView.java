package com.vaadin.tutorial.spring.ui.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.spring.ui.view.dashboard.DashboardView;
import com.vaadin.tutorial.spring.ui.view.list.ListView;

//@PWA(name = "VaadinCRM", shortName = "VaadinCRM")
//@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/styles.css")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
public class MainView extends AppLayout {

  public MainView() {
    H1 logo = new H1("Vaadin CRM");
    logo.addClassName("logo");
    Anchor logout = new Anchor("/logout", "Log out");

    HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(), logo, logout);
    layout.addClassName("header");
    layout.expand(logo);
    layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    layout.setWidth("100%");

    addToNavbar(layout);
    addToDrawer(new VerticalLayout(
        new RouterLink("List", ListView.class),
        new RouterLink("Dashboard", DashboardView.class)));
  }
}
