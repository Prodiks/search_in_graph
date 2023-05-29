module main_app.search_in_graph {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens main_app.search_in_graph to javafx.fxml;
    exports main_app.search_in_graph;
}