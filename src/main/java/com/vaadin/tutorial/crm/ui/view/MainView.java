package com.vaadin.tutorial.crm.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.tutorial.crm.ui.view.dashboard.DashboardView;
import com.vaadin.tutorial.crm.ui.view.list.ListView;

@PWA(name = "VaadinCRM", shortName = "VaadinCRM")
@CssImport("./styles/styles.css")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
public class MainView extends AppLayout {

  public MainView() {
    createNavbar();
    createDrawer();
  }

  private void createNavbar() {
    H1 logo = new H1("Vaadin CRM");
    logo.addClassName("logo");
    Anchor logout = new Anchor("/logout", "Log out");

    HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(), logo, logout);
    layout.addClassName("header");
    layout.expand(logo);
    layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    layout.setWidth("100%");

    addToNavbar(layout);
  }

  private void createDrawer() {
    Button darkModeToggle = new Button("Dark mode");
    darkModeToggle.setIcon(VaadinIcon.LIGHTBULB.create());
    darkModeToggle.addClickListener(c -> {
      ThemeList themeList = UI.getCurrent().getElement().getThemeList();
      if (themeList.contains("dark")) {
        themeList.remove("dark");
        darkModeToggle.setText("Dark Mode");
      } else {
        themeList.add("dark");
        darkModeToggle.setText("Light Mode");
      }
    });

    Div drawerLayout = new Div(
        new VerticalLayout(new RouterLink("List", ListView.class), new RouterLink("Dashboard", DashboardView.class)),
        darkModeToggle);
    drawerLayout.addClassName("drawer-content");
    addToDrawer(drawerLayout);
  }
}
