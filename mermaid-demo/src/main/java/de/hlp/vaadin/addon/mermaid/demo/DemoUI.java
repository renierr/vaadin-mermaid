package de.hlp.vaadin.addon.mermaid.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.hlp.vaadin.addon.mermaid.MermaidChart;

import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("Mermaid Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

  private static final String DEMO_CHART = "graph LR;\n" +
      "  a(Find elements)-->b{Processed}\n" +
      "  b-->|Yes|c(Leave element)\n" +
      "  b-->|No |d(Transform)\n" +
      "  click b \"http://www.github.com\" \"This is a tooltip for a link\"";

  @WebServlet(value = "/*", asyncSupported = true)
  @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
  public static class Servlet extends VaadinServlet {
  }

  @Override
  protected void init(VaadinRequest request) {

    // Initialize our new UI component
    TextArea textArea = new TextArea("Chart Code:");
    textArea.setValue(DEMO_CHART);
    textArea.setWidth(800f,Unit.PIXELS);
    final MermaidChart component = new MermaidChart(DEMO_CHART);
    textArea.addValueChangeListener(valueChangeEvent -> component.render(valueChangeEvent.getValue()));

    component.addStateClickListener(val -> Notification.show("You clicked on id: " + val));
    // Show it in the middle of the screen
    final VerticalLayout layout = new VerticalLayout();
    layout.setStyleName("demoContentLayout");
    layout.setSizeFull();
    layout.addComponent(textArea);
    layout.addComponentsAndExpand(component);
    layout.setComponentAlignment(textArea, Alignment.MIDDLE_CENTER);
    layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    setContent(layout);
  }
}
