package org.vaadin.addon.mermaid;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

import java.util.LinkedList;
import java.util.List;

@JavaScript({"mermaid.min.js", "d3.min.js", "mermaid_connector.js", "simg.min.js",})
public class MermaidChart extends AbstractJavaScriptComponent {

  private final List<StateClickListener> stateClickListenerList = new LinkedList<>();
  private String data;

  public interface StateClickListener {
    void onStateClick(String id);
  }

  public MermaidChart(String data) {
    this.data = data;
    addJsFunctions();
  }

  // We must override getState() to cast the state to MermaidState
  @Override
  protected MermaidState getState() {
      return (MermaidState) super.getState();
  }

  @Override
  public void attach() {
    if (data != null) {
      getState().data = data;
    }
    super.attach();
  }

  private void addJsFunctions() {
    addFunction("onStateClick", (JavaScriptFunction) arguments ->
        stateClickListenerList.forEach(listener -> listener.onStateClick(arguments.getString(0))));
  }

  public void render(String data) {
    if (data != null) {
      getState().data = data;
    }
  }

  public void downloadImage(String filename) {
    callFunction("downloadAsImage", filename);
  }

  public void addStateClickListener(StateClickListener listener) {
    stateClickListenerList.add(listener);
    listenerState();
  }

  public void removeStateClickListener(StateClickListener listener) {
    stateClickListenerList.remove(listener);
    listenerState();
  }

  private void listenerState() {
    getState().callListener = !stateClickListenerList.isEmpty();
  }

}
