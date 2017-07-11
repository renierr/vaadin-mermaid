window.de_hlp_vaadin_addon_mermaid_MermaidChart = function() {
  var element = this.getElement();
  var self = this;
  var insertSvg;
  var nextId = 0;
  var id = 'mermaidChart';
  var callListener = false;

  this.onStateChange = function() {
    if (typeof insertSvg === 'undefined') {
      mermaid.initialize({
        startOnLoad: false,
        flowchart:{
          useMaxWidth: false,
          htmlLabels: false
        }
      });
      insertSvg = function insertSvg(svgCode, bindFunctions) {
        element.innerHTML = svgCode;
        bindFunctions(element);
        // every element state get a click handler
        d3.select(element).selectAll('g.node').each(function(d) {
          var d3This = d3.select(this);
          var oldOnClick = d3This.on('click');
          d3This.on('click', function () {
            if (oldOnClick) {
              try {
                oldOnClick();
              } catch(err) {}
            }
            if (callListener) self.onStateClick(this.id);
          }, false);
        });
      };
      id += nextId++;
    }
    mermaid.render(id, this.getState().data, insertSvg, element);
    callListener = this.getState().callListener;
  }
};