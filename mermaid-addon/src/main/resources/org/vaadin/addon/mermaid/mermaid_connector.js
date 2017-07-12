window.org_vaadin_addon_mermaid_MermaidChart = function() {
  var element = this.getElement();
  var self = this;
  var insertSvg;
  var nextId = 0;
  var id = 'mermaidChart';
  var callListener = false;

  if (navigator.userAgent.search("MSIE") >= 0) {
    Object.defineProperty(SVGElement.prototype, 'outerHTML', {
      get: function () {
        var $node, $temp;
        $temp = document.createElement('div');
        $node = this.cloneNode(true);
        $temp.appendChild($node);
        var ret = $temp.innerHTML;
        // cleanup for IE
        ret = ret.replace(new RegExp('NS\\d+:xmlns:xml="http://www.w3.org/XML/1998/namespace"', 'gi'), "");
        ret = ret.replace(new RegExp('NS\\d+:xmlns:ns\\d+="http://www.w3.org/XML/1998/namespace"', 'gi'), "");
        ret = ret.replace(new RegExp('xmlns:ns\\d+="http://www.w3.org/XML/1998/namespace"', 'gi'), "");
        ret = ret.replace(new RegExp('xml:', 'gi'), "");
        ret = ret.replace(new RegExp('ns\\d+:', 'gi'), "");
        ret = ret.replace(new RegExp('xmlns:xml="http://www.w3.org/XML/1998/namespace"', 'gi'), "");
        ret = ret.replace(new RegExp('NS\\d+:ns\\d+:', 'gi'), "");
        ret = ret.replace(new RegExp('xmlns:NS\\d+=""', 'gi'), "");
        console.log(ret);
        return ret;
      },
      enumerable: false,
      configurable: true
    });
  }

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
  };

  this.downloadAsImage = function(filename) {
    filename = filename || "svg.png";
    console.log("down element: " + element.children[0]);
    var simg = new Simg(element.children[0]);
    simg.download(filename);
  }
};