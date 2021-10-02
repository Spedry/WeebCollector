module sk.spedry.weebcollector {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.apache.logging.log4j;
    requires com.google.gson;

    exports sk.spedry.weebcollector to javafx.fxml, javafx.controls, javafx.graphics;

    opens sk.spedry.weebcollector.app.controllers to javafx.fxml;
    opens sk.spedry.weebcollector.app.controllers.cell to javafx.fxml;
    opens sk.spedry.weebcollector.app.controllers.preferencescontrollers to javafx.fxml;
}