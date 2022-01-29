open module sk.spedry.weebcollector {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires java.desktop;

    exports sk.spedry.weebcollector to javafx.fxml, javafx.controls, javafx.graphics;
    /*opens sk.spedry.weebcollector.app.controllers.util to com.google.gson;

    opens sk.spedry.weebcollector.app.controllers to javafx.fxml;
    opens sk.spedry.weebcollector.app.controllers.cell to javafx.fxml;
    opens sk.spedry.weebcollector.app.controllers.preferencescontrollers to javafx.fxml;
    opens sk.spedry.weebcollector.app.controllers.util.exteders to javafx.fxml;*/
}