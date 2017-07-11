package de.hlp.vaadin.addon.mermaid.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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

  private static final String DEMO_CHART2 = "graph TB\n" +
      "    sq[Square shape] --> ci((Circle shape))\n" +
      "\n" +
      "    subgraph A subgraph\n" +
      "        od>Odd shape]-- Two line<br>edge comment --> ro\n" +
      "        di{Diamond with <br/> line break} -.-> ro(Rounded<br>square<br>shape)\n" +
      "        di==>ro2(Rounded square shape)\n" +
      "    end\n" +
      "\n" +
      "    %% Notice that no text in shape are added here instead that is appended further down\n" +
      "    e --> od3>Really long text with linebreak<br>in an Odd shape]\n" +
      "\n" +
      "    %% Comments after double percent signs\n" +
      "    e((Inner / circle<br>and some odd <br>special characters)) --> f(,.?!+-*ز)\n" +
      "\n" +
      "    cyr[Cyrillic]-->cyr2((Circle shape Начало));\n" +
      "\n" +
      "     classDef green fill:#9f6,stroke:#333,stroke-width:2px;\n" +
      "     classDef orange fill:#f96,stroke:#333,stroke-width:4px;\n" +
      "     class sq,e green\n" +
      "     class di orange";

  private static final String DEMO_CHART3 = "sequenceDiagram\n" +
      "    Alice ->> Bob: Hello Bob, how are you?\n" +
      "    Bob-->>John: How about you John?\n" +
      "    Bob--x Alice: I am good thanks!\n" +
      "    Bob-x John: I am good thanks!\n" +
      "    Note right of John: Bob thinks a long<br/>long time, so long<br/>that the text does<br/>not fit on a row.\n" +
      "\n" +
      "    Bob-->Alice: Checking with John...\n" +
      "    Alice->John: Yes... John, how are you?";

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
    component.setSizeFull();
    component.addStyleName("v-align-center");
    textArea.addValueChangeListener(valueChangeEvent -> component.render((String)valueChangeEvent.getProperty().getValue()));

    Button demo1 = new Button("Demo Chart 1", click -> textArea.setValue(DEMO_CHART));
    Button demo2 = new Button("Demo Chart 2", click -> textArea.setValue(DEMO_CHART2));
    Button demo3 = new Button("Demo Chart 3", click -> textArea.setValue(DEMO_CHART3));
    HorizontalLayout demoButtons = new HorizontalLayout(demo1, demo2, demo3);

    component.addStateClickListener(val -> Notification.show("You clicked on id: " + val));
    // Show it in the middle of the screen
    final VerticalLayout layout = new VerticalLayout();
    layout.setStyleName("demoContentLayout");
    layout.setSizeFull();
    layout.addComponent(textArea);
    layout.addComponent(demoButtons);
    layout.addComponent(component);
    layout.setExpandRatio(component, 1f);
    layout.setComponentAlignment(textArea, Alignment.MIDDLE_CENTER);
    layout.setComponentAlignment(demoButtons, Alignment.MIDDLE_CENTER);
    layout.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
    setContent(layout);
  }
}
