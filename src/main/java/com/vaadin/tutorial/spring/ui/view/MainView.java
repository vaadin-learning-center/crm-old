package com.vaadin.tutorial.spring.ui.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.tutorial.spring.ui.view.dashboard.DashboardView;
import com.vaadin.tutorial.spring.ui.view.list.ListView;

@CssImport("./styles/styles.css")
//@PWA(name = "VaadinCRM", shortName = "VaadinCRM")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AppLayout {

  public MainView() {
    H1 logo = new H1("Vaadin CRM");
    logo.addClassName("logo");
    addToNavbar(new DrawerToggle(), logo);
    addToDrawer(new VerticalLayout(
        new RouterLink("List", ListView.class),
        new RouterLink("Dashboard", DashboardView.class)));
  }
}
