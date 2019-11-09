package com.vaadin.tutorial.spring.ui.view.dashboard;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.spring.ui.view.MainView;

@Route(value = "dashboard", layout = MainView.class)
public class DashboardView extends VerticalLayout {

  public DashboardView() {
    addClassName("dashboard-view");
    add(new H2("Dashboard"));
  }
}
