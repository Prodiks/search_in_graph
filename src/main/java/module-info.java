module main_app.search_in_graph {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
    requires org.jgrapht.core;
    requires com.github.vlsi.mxgraph.jgraphx;
    requires java.desktop;

    opens main_app.search_in_graph to javafx.fxml;
    exports main_app.search_in_graph;
}